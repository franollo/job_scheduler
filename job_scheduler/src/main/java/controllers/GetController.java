package main.java.controllers;

import main.java.model.*;
import main.java.modules.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful API controller. It handles HTTP requests with GET metod.
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/get")
public class GetController {
    @Autowired
    private ProductsModule productsModule;

    @Autowired
    private OrdersModule ordersModule;

    @Autowired
    private ResourcesModule resourcesModule;

    @Autowired
    private ProductionPlansModule productionPlansModule;

    @Autowired
    private UsersModule usersModule;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public @ResponseBody List<Order> orders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return ordersModule.getUserOrders(user);
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public @ResponseBody List<ResourceType> resources() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return resourcesModule.getUserResourceTypes(user);
    }

    @RequestMapping(value = "/productionplans", method = RequestMethod.GET)
    public @ResponseBody List<ProductionPlan> productionPlans() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return productionPlansModule.getUserProductionPlans(user);
    }

    @RequestMapping(value = "/productionplan/{productionPlanId}", method = RequestMethod.GET)
    public @ResponseBody ProductionPlan productionPlan(@PathVariable int productionPlanId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return productionPlansModule.getProductionPlan(productionPlanId, user);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody List<Product> products() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return productsModule.getUserProducts(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody User userInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return usersModule.getUser(auth.getName());
    }
}

