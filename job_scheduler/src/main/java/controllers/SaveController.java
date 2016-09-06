package main.java.controllers;

import main.java.dao.model.*;
import main.java.model.*;
import main.java.modules.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Marcin Frankowski
 */

@RestController
@RequestMapping("/save")
public class SaveController {

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

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public @ResponseBody Item item(@RequestBody Item item) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return productionPlansModule.saveItem(item, user);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public @ResponseBody Order order(@RequestBody Order order) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return ordersModule.saveOrder(order, user);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public @ResponseBody Product product(@RequestBody Product product) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return productsModule.saveProduct(product, user);
    }

    @RequestMapping(value = "/productionplan", method = RequestMethod.POST)
    public @ResponseBody ProductionPlan productionPlan(@RequestBody ProductionPlan productionPlan) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return productionPlansModule.saveProductionPlan(productionPlan, user);
    }

    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public @ResponseBody Resource resource(@RequestBody Resource resource) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return resourcesModule.saveResource(resource, user);
    }

    @RequestMapping(value = "/resourcetype", method = RequestMethod.POST)
    public @ResponseBody ResourceType resourcetype(@RequestBody ResourceType resourceType) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = usersModule.getUser(auth.getName());
        return resourcesModule.saveResourceType(resourceType, user);
    }
}
