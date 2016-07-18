package com.marcin.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/getdata")
public class GetDataController {
//    @Autowired
//    private JdbcResourceDAO resourceDAO;
//
//    @Autowired
//    private JdbcJobDAO jobDAO;
//
//    @Autowired
//    private JdbcTaskDAO taskDAO;
//
//    @Autowired
//    private JdbcOrderDAO orderDAO;
//
//    @Autowired
//    private JdbcItemDAO itemDAO;

//    @RequestMapping(value = "/orders", method = RequestMethod.GET)
//    public List<Order> getOrders() {
//        System.out.println("Debug Message from /orders");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        return orderDAO.getUserOrders(name);
//    }
//
//    @RequestMapping(value = "/resources", method = RequestMethod.GET)
//    public List<Resource> getResources() {
//        System.out.println("Debug Message from /resources");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        System.out.println(name);
//        return resourceDAO.getUserResources(name);
//    }
//
//    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
//    public List<Job> getJobs() {
//        System.out.println("Debug Message from /jobs");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        List<Job> jobs = jobDAO.getUserJobs(name);
//        for (Job job : jobs) {
//            List<Task> tasks = taskDAO.getTasksByJobId(job.getJobId());
//            for (Task task : tasks) {
//                task.setResource(resourceDAO.getByID(task.getResourceId()));
//            }
//            job.setTasks(tasks);
//        }
//        return jobs;
//    }
//
////    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
////    public ResponseEntity<UserOld> getUser() {
////        System.out.println("Debug Message from /userinfo");
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        String name = SecurityContextHolder.getContext().getAuthentication().getName();
////        HttpStatus status = name != "anonymousUser" ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
////        return new ResponseEntity<UserOld>(userDAO.getUserByLogin(name), status);
////    }
//
//    @RequestMapping(value = "/order", method = RequestMethod.POST)
//    public VisContent getOrder(@RequestBody int orderId) {
//        System.out.println("Debug Message from /order");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//       // userDAO.setOrderInUse(name, orderId);
//        return new VisContent(itemDAO.getOrderItems(orderId),
//                resourceDAO.getOrderGroups(orderId),
//                orderDAO.getOrder(orderId));
//    }
}

