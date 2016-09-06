package test.java.modules;

import main.java.model.Product;
import main.java.model.ProductOperation;
import main.java.modules.ProductsModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin Frankowski on 06.09.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:WEB-INF/test/test-context.xml")
public class ProductsModuleUnitTest {
    private Product product;
    private Product productNull;
    private ProductsModule productsModule;
    private Method method;

    @Before
    public void beforeTest() {
        try {
            method = ProductsModule.class.getDeclaredMethod("orderProductOperations", Product.class);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        productsModule = new ProductsModule();
        product = new Product();
        productNull = new Product();
        List<ProductOperation> productOperations = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            ProductOperation productOperation = new ProductOperation();
            productOperation.setName(Integer.toString(i));
            productOperation.setDuration(Duration.ofSeconds(i));
            if(i == 2) {
                productOperation.setSequential(false);
            }
            else {
                productOperation.setSequential(true);
            }
            productOperations.add(productOperation);
        }
        product.setProductOperations(productOperations);
    }

    @Test
    public void testOrderProductOperations() throws InvocationTargetException, IllegalAccessException {
        method.invoke(productsModule, product);
        method.invoke(productsModule, productNull);
        int counter = 0;
        int number = 0;
        for(ProductOperation productOperation : product.getProductOperations()) {
            assertNotNull(productOperation.getName());
            assertNotNull(productOperation.getDuration());
            if(counter == 2) {
                assertNull(productOperation.getOperationNumber());
            }
            else {
                assertNotNull(productOperation.getOperationNumber());
            }
            counter++;
        }
        for (ProductOperation productOperation : product.getProductOperations()) {
            if(productOperation.isSequential() == true) {
                number++;
            }
        }
        assertEquals(number, 5);
    }
}
