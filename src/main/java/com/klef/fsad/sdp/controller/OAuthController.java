package com.klef.fsad.sdp.controller;

import com.klef.fsad.sdp.model.Admin;
import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.model.Manager;
import com.klef.fsad.sdp.model.OAuthUser;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.repository.EmployeeRepository;
import com.klef.fsad.sdp.repository.ManagerRepository;
import com.klef.fsad.sdp.repository.OAuthUserRepository;
import com.klef.fsad.sdp.security.JWTUtilizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JWTUtilizer jwtService;

    @GetMapping("/verify")
    public ResponseEntity<?> verify(OAuth2AuthenticationToken token) {

        OAuth2User user = token.getPrincipal();

        String email = user.getAttribute("email");
//        System.out.println(user.getAttributes());
//
//        System.out.println("=================================");
//        System.out.println("Google Email = " + email);
//        System.out.println("=================================");

        // ---------- Admin ----------

        Optional<Admin> admin = adminRepository.findByEmail(email);

        if (admin.isPresent()) {

            String jwt = jwtService.generateJWTToken(
                    admin.get().getUsername(),
                    "ADMIN"
            );

            Map<String, Object> res = new HashMap<>();

            res.put("message", "Google Login Successful");
            res.put("role", "ADMIN");
            res.put("token", jwt);
            res.put("data", admin.get());

            return ResponseEntity.ok(res);
        }

        // ---------- Manager ----------

        Optional<Manager> manager = managerRepository.findByEmail(email);

        if (manager.isPresent()) {

            String jwt = jwtService.generateJWTToken(
                    manager.get().getUsername(),
                    "MANAGER"
            );

            Map<String, Object> res = new HashMap<>();

            res.put("message", "Google Login Successful");
            res.put("role", "MANAGER");
            res.put("token", jwt);
            res.put("data", manager.get());

            return ResponseEntity.ok(res);
        }

        // ---------- Employee ----------

        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isPresent()) {

            if (!employee.get().getAccountstatus()
                    .equalsIgnoreCase("Accepted")) {

                return ResponseEntity.status(401)
                        .body(Map.of(
                                "message",
                                "Account Not Approved"
                        ));
            }

            String jwt = jwtService.generateJWTToken(
                    employee.get().getUsername(),
                    "EMPLOYEE"
            );

            Map<String, Object> res = new HashMap<>();

            res.put("message", "Google Login Successful");
            res.put("role", "EMPLOYEE");
            res.put("token", jwt);
            res.put("data", employee.get());

            return ResponseEntity.ok(res);
        }

        return ResponseEntity.status(401)
                .body(Map.of(
                        "message",
                        "No account registered with this Google email."
                ));
    }

}


/*  Endpoints:

http://localhost:8080/oauth/login

http://localhost:8080/oauth/verify

 */
