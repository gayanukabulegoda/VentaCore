package lk.ijse.ventacorebackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ventacorebackend.bo.BOFactory;
import lk.ijse.ventacorebackend.bo.custom.CustomerBO;
import lk.ijse.ventacorebackend.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(urlPatterns = "/customer", loadOnStartup = 2)
public class CustomerController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @Override
    public void init() throws ServletException {
        logger.info("Customer Controller Initialized");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria", req.getRequestURI());
        }
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            writer.write(String.valueOf(customerBO.saveCustomer(customerDTO)));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Customer Saved Successfully");
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while saving Customer", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var customerId = req.getParameter("customerId");
        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");

            if (customerId != null) {
                jsonb.toJson(customerBO.getCustomerData(customerId), writer);
                resp.setStatus(HttpServletResponse.SC_OK);
                logger.info("Customer Retrieved Successfully");
            } else {
                jsonb.toJson(customerBO.getAllCustomers(), writer);
                resp.setStatus(HttpServletResponse.SC_OK);
                logger.info("All Customers Retrieved Successfully");
            }
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while retrieving Customer", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            if (customerBO.updateCustomer(customerDTO)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Customer Updated Successfully");
            } else {
                writer.write("Customer Update Failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Customer Update Failed");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while updating Customer", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            var customerId = req.getParameter("customerId");
            if(customerBO.deleteCustomer(customerId)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Customer Deleted Successfully");
            } else {
                writer.write("Customer Delete Failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Customer Delete Failed");
            }
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while deleting Customer", e);
            e.printStackTrace();
        }
    }
}
