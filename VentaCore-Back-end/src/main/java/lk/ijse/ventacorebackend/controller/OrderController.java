package lk.ijse.ventacorebackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ventacorebackend.bo.BOFactory;
import lk.ijse.ventacorebackend.bo.custom.OrderBO;
import lk.ijse.ventacorebackend.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(urlPatterns = "/order", loadOnStartup = 1)
public class OrderController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(OrderController.class);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    @Override
    public void init() throws ServletException {
        logger.info("Order Controller Initialized");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria", req.getRequestURI());
        }
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
            writer.write(String.valueOf(orderBO.saveOrder(orderDTO)));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Order Saved Successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while saving Order", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");
            jsonb.toJson(orderBO.getAllOrders(), writer);
            resp.setStatus(HttpServletResponse.SC_OK);
            logger.info("All Orders Retrieved Successfully");
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while retrieving Orders", e);
            e.printStackTrace();
        }
    }
}
