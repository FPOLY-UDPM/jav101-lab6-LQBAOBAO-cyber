package fpoly.servlet;

import dao.DepartmentsDAO;
import entity.Departments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {
        "/departments",
        "/department/*",
        "/department/delete/*",
        "/department/insert",
        "/department/update"
})

public class DepartmentServlet  extends HttpServlet {
    private DepartmentsDAO deptDAO = new DepartmentsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException, IOException {

//        // Luôn khởi tạo danh sách rỗng để tránh lỗi NullPointerException ở tầng hiển thị
        List<Departments> resultList = new ArrayList<>();

//
//        // 1. Lấy Servlet Path để nhận diện hành vi gốc
//        // Ví dụ: gọi /departments -> trả về "/departments"
//        // Ví dụ: gọi /department/id/IT -> trả về "/department"
        String servletPath = req.getServletPath();

//
//        // 2. Lấy Path Info để lấy phần tham số mở rộng phía sau
//        // Ví dụ: gọi /department/id/IT -> trả về "/id/IT"
//        // Ví dụ: gọi /departments thô -> trả về null
        String pathInfo = req.getPathInfo();

//
        try {

//            // TRƯỜNG HỢP 1: Người dùng gọi thẳng vào /departments -> Liệt kê tất cả
            if (servletPath.equals("/departments")) {

                resultList = deptDAO.findAll();

            }
            // DELETE
            else if (servletPath.equals("/department/delete")) {

                String id = pathInfo.substring(1);

                deptDAO.delete(id);

                resp.sendRedirect(req.getContextPath() + "/departments");
                return;
            }


//            // TRƯỜNG HỢP 2: Người dùng gọi vào nhánh lọc dữ liệu /department/*
            else if (servletPath.equals("/department")) {

                if (pathInfo != null && !pathInfo.equals("/")) {

//                    // Tách chuỗi pathInfo (Ví dụ: "/id/IT" -> ["", "id", "IT"])
                    String[] pathParts = pathInfo.split("/");

//                    // KIỂM TRA AN TOÀN: Phải đủ tối thiểu 3 phần tử trong mảng mới xử lý
                    if (pathParts.length >= 3) {

                        String fieldname = pathParts[1].trim().toLowerCase();
                        String keyword = pathParts[2].trim();

//                        // RẼ NHÁNH ĐIỀU PHỐI TÌM KIẾM ĐỘNG
                        switch (fieldname) {

                            case "id":

                                Departments d = deptDAO.findById(keyword);

                                if (d != null) {
                                    resultList.add(d);
                                }

                                break;

                            case "name":

                                resultList = deptDAO.findByName(keyword);

                                break;

                            default:

                                req.setAttribute(
                                        "errorMessage",
                                        "Tiêu chí tìm kiếm '" + fieldname + "' không được hỗ trợ!"
                                );

                                break;
                        }

                    } else {

//                        // Người dùng gõ thiếu (Ví dụ: /department/id hoặc /department/)
                        req.setAttribute(
                                "errorMessage",
                                "Đường dẫn không hợp lệ! Vui lòng nhập đúng cấu trúc: /department/{tiêu_chí}/{từ_khóa}"
                        );
                    }

                } else {

//                    // Người dùng gõ /department hoặc /department/ thô không có tham số
                    req.setAttribute(
                            "errorMessage",
                            "Vui lòng chỉ định tiêu chí lọc dữ liệu (Ví dụ: /department/id/IT)!"
                    );
                }
            }

//
//            // Đẩy duy nhất mảng kết quả ra cho JSP hiển thị
            req.setAttribute("departments", resultList);

        } catch (Exception e) {

            e.printStackTrace();

            req.setAttribute(
                    "errorMessage",
                    "Hệ thống gặp lỗi khi truy vấn: " + e.getMessage()
            );
        }

//        // Forward sang duy nhất một view hiển thị danh sách dùng chung
        req.getRequestDispatcher("/views/department-list.jsp")
                .forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String servletPath = req.getServletPath();

        try {

            if (servletPath.equals("/department/insert")) {

                Departments dept = new Departments();

                dept.setId(req.getParameter("id"));
                dept.setName(req.getParameter("name"));
                dept.setDescription(req.getParameter("description"));

                deptDAO.insert(dept);
            }

            else if (servletPath.equals("/department/update")) {

                Departments dept = new Departments();

                dept.setId(req.getParameter("id"));
                dept.setName(req.getParameter("name"));
                dept.setDescription(req.getParameter("description"));

                deptDAO.update(dept);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/departments");
    }
}
