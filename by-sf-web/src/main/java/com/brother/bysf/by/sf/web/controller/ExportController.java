package com.brother.bysf.by.sf.web.controller;

import com.brother.bysf.by.sf.web.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author xfun
 * @date 2018-04-28
 */
@RestController
@RequestMapping(path = "/export")
public class ExportController {


    @PersistenceContext
    EntityManager entityManager;

    @RequestMapping("/")
    public StreamingResponseBody handleRequest () {

        return out -> {
            for (int i = 0; i < 1000; i++) {
                out.write((Integer.toString(i) + " - ")
                        .getBytes());
                out.flush();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @RequestMapping(value = "/user/stream/csv", method = RequestMethod.GET)
    public void csvStream(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/csv");
        response.addHeader("Content-Disposition", "attachment; filename=user_details.csv");
        response.setCharacterEncoding("UTF-8");
        try (Stream<User> userDetailsStream = this.userStream();) {
            PrintWriter out = response.getWriter();
            userDetailsStream.forEach(userDetail -> {
                out.write(userDetail.toString());
                out.write("\n");
                entityManager.detach(userDetail);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            out.flush();
            out.close();
            userDetailsStream.close();
        } catch (IOException ix) {
            throw new RuntimeException("There is an error while downloading user_details.csv", ix);
        }
    }

    @RequestMapping(value = "/user/list/csv", method = RequestMethod.GET)
    public void csvList(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/csv");
        response.addHeader("Content-Disposition", "attachment; filename=user_details.csv");
        response.setCharacterEncoding("UTF-8");
        try {
            List<User> userDetailsStream = this.userList();
            PrintWriter out = response.getWriter();
            userDetailsStream.forEach(userDetail -> {
                out.write(userDetail.toString());
                out.write("\n");
                entityManager.detach(userDetail);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            out.flush();
            out.close();
        } catch (IOException ix) {
            throw new RuntimeException("There is an error while downloading user_details.csv", ix);
        }
    }

    private List<User> userList(){
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setUserId("id-" + i);
            user.setUserName("name-" + i);
            userList.add(user);
        }
        return userList;
    }

    private Stream<User> userStream(){
        return this.userList().stream();
    }
}
