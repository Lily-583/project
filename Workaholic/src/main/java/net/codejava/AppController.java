package net.codejava;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private TaskRepository taskRepo;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/main")
	public String backMain() {
		return "index";
	}


	@GetMapping("/register")
	public String showRegistrationForm() {
		return "pick_roll";
	}


	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/guest")
	public String guest() {
		return "guest";
	}

	@GetMapping("/register_user")
	public String registerUser(Model model){
		model.addAttribute("user", new User());
		return "signup_form_user";
	}

	@GetMapping("/register_manager")
	public String registerManager(Model model){
		model.addAttribute("manager", new User());
		return "signup_form_manager";
	}

	@PostMapping("/process_user_register")
	public String processUserRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setManager(false);
		userRepo.save(user);
		return "register_success";
	}

	@PostMapping("/process_manager_register")
	public String processManagerRegister(User manager) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(manager.getPassword());
		manager.setPassword(encodedPassword);
		manager.setManager(true);
		userRepo.save(manager);
		return "register_success";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	@GetMapping ("/viewTask")
	public String viewTask(Model model) {
		CustomUserDetails temp= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Task> listTasks = taskRepo.findAll();

		// create a new list to store all the claimed tasks
		List<Task> UrgentTasks = new ArrayList<>();
		for(Task i: listTasks) {
			if(i.getUrgent().equals("yes")) {
				UrgentTasks.add(i);
			}
		}

		// create a new list to store all the claimed tasks
		List<Task> NormalTasks = new ArrayList<>();
		for(Task i: listTasks) {
			if(i.getUrgent().equals("no")) {
				NormalTasks.add(i);
			}
		}

		model.addAttribute("listTasks", listTasks);
		model.addAttribute("urgentTasks", UrgentTasks);
		model.addAttribute("normalTasks", NormalTasks);
		if(temp.getManager()) {
			return "tasks_manager";
		}else{
			return "tasks_member";
		}
	}

	@GetMapping("/add_a_task")
	public String addATask(Model model) {
		model.addAttribute("task", new Task());
		return "add_task";
	}

	@PostMapping("/process_add_task")
	public ModelAndView processAddTask(Task task) {
		taskRepo.save(task);
		return new ModelAndView("redirect:/viewTask");
	}


}
