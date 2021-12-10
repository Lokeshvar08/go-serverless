package com.restaurant.server.restaurantservermanager.service.transactions.employeeCreation;

import com.restaurant.server.restaurantservermanager.model.Restaurant;
import com.restaurant.server.restaurantservermanager.model.User;
import com.restaurant.server.restaurantservermanager.service.errors.ServiceErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeCreationImpl  implements EmployeeCreation {


    @Autowired
    private EmployeeCreationTransaction employeeCreationTransaction;

    public EmployeeCreationTransaction getEmployeeCreationTransactionImpl() {
        return employeeCreationTransaction;
    }

    public void setEmployeeCreationTransactionImpl(EmployeeCreationTransaction employeeCreationTransactionImpl) {
        this.employeeCreationTransaction = employeeCreationTransactionImpl;
    }

    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = { ServiceErrorHandler.class })
    @Override
    public void createEmployee(
            String username,
            String password,
            String name,
            String phone,
            String role,
            Restaurant restaurant) throws Exception {

        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(10).encode(password));
        user.setName(name);
        user.setPhone(phone);
        user.setRole(User.Role.valueOf(role));
        user.setStatus(true);

        try {
            employeeCreationTransaction.createUser(user);
            employeeCreationTransaction.linkUserWithRestaurant(user, restaurant);
        } catch(Exception e){
            throw new ServiceErrorHandler("Employee Creation Failed!");
        }
    }
}
