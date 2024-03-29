import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");

    @BeforeEach
    public void beforeEach() {
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime searchTime = LocalTime.parse("12:30:00");
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(searchTime);
        assertTrue(spyRestaurant.isRestaurantOpen());
        Mockito.verify(spyRestaurant, Mockito.times(1)).getCurrentTime();
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime searchTime = LocalTime.parse("09:30:00");
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(searchTime);
        assertFalse(spyRestaurant.isRestaurantOpen());
        Mockito.verify(spyRestaurant, Mockito.times(1)).getCurrentTime();
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void calculateOrderTotal_method_should_return_correct_total_if_items_are_provided() {
        String[] itemNames = {"Sweet corn soup", "Vegetable lasagne"};
        int orderTotal = restaurant.calculateOrderTotal(itemNames);
        assertEquals(388, orderTotal);
    }

    @Test
    public void calculateOrderTotal_method_should_return_0_if_items_array_provided_is_empty() {
        String[] itemNames = {};
        int orderTotal = restaurant.calculateOrderTotal(itemNames);
        assertEquals(0, orderTotal);
    }
}