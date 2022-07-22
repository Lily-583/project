#!/bin/bash
declare -a hostnames=()
declare -a containers=()
declare -a selected_containers=()
generate_containers()
{
	
	for hostname in "${hostnames[@]}"
	do
	  for i in $(seq 0 $cpu_num $cpu_upper_limit)
	  do
	    j=$(($i + $cpu_num-1))
	    # echo "$i"
	    # echo "$i-$j"
	    echo "$hostname-container-$i-$j"
	    container_name=$hostname-container-$i-$j
	    containers+=($container_name)
	  done
	done
}

select_containers()
{
	
	for index in $(shuf --input-range=0-$(( ${#containers[*]} - 1 )) -n ${num_of_chosen_containers})
	do
	    selected_containers+=(${containers[$index]})
	    # echo ${containers[$index]}
	done
}

spin_up_worker()
{
	
	for hostname in "${hostnames[@]}"
	do
	  # kill all containers with name include container keyword
	  ssh poseidon@$worker_host "docker ps --filter name=container* --filter status=running -aq | xargs docker stop"
	  # for i in $(seq 0 1 $cpu_upper_limit)
	  for i in $(seq 0 $cpu_num $cpu_upper_limit)
	  do
	    #j=$i
	    j=$(($i + $cpu_num-1))
	    # echo "$i"
	    # echo "$i-$j"
	    echo "$hostname-container-$i-$j"
	    container_name="$hostname-container-$i-$j"

	    if [[ " ${selected_containers[*]} " == *"$container_name"* ]];
	    then
	      # actual_cpus=2
	      echo "Add CPU anomaly to container $container_name"
	    else
	      actual_cpus=$cpu_num
	      echo "No cpu anomaly to container $container_name"
	    fi 

	    # echo "$condor_host"
	    ssh poseidon@$worker_host "docker run \
	      -e CONDOR_HOST=$condor_host \
	      -e NUM_CPUS=$cpu_num \
	      -e POOL_PASSWORD=$pool_pass \
	      -e NETWORK_LIMIT=$network_limit \
	      -v $volume_details \
	      -h "$hostname-container-$i-$j" \
	      --rm \
	      --detach \
	      --cap-add=NET_ADMIN \
	      --cpuset-cpus=$i-$j \
	      --cpus=$actual_cpus \
	      --memory=$memory_size \
	      --net=$network_name \
	      --name "$hostname-container-$i-$j" \
	      papajim/poseidon-execute"
	  done
	done
}

main()
{
	actual_cpus=0
	# generate_containers
	PS3="What is the type of anomaly?: "
	select anomaly_type in cpu_anomaly hard_disk_anomaly no_anomaly;
	do echo "The type of anomaly is $anomaly_type"
	break
    done
    

		read -p "cpu_upper_limit: " cpu_upper_limit
		if [ "$anomaly_type" == "cpu_anomaly" ]; then
			read -p "num_of_chosen_containers: " num_of_chosen_containers
			read -p "num of cores for each container in abnormal case: " actual_cpus
		fi
		read -p "num of cores for each container in normal case: " cpu_num
		read -p "ip address for worker host: " worker_host
		read -p "hostnames (seperate by white space) " line
		# read line 
		hostnames+=(${line})

		# network parameters
		interface=eno1np0
		subnet=10.100.100.0/22
		gateway=10.100.101.1
		# echo "${containers[1]}"
		
		# condor worker parameters
		condor_host=10.100.101.203
		
		# use the actual_cpu to introduce anomalies by allocating less than 4 cpus
		memory_size=16g
		volume_details=/home/poseidon/volumne/:/home/poseidon/volumne/
		pool_pass=p0s31d0n
		network_name=host
		network_limit=1000

		generate_containers
		if [ "$anomaly_type" == "cpu_anomaly" ]; then
			select_containers
		fi
		#spin_up_worker 
    
}

main