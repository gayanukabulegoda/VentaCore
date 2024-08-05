package lk.ijse.ventacorebackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ventacorebackend.bo.BOFactory;
import lk.ijse.ventacorebackend.bo.custom.ItemBO;
import lk.ijse.ventacorebackend.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(urlPatterns = "/item", loadOnStartup = 2)
public class ItemController extends HttpServlet {
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @Override
    public void init() throws ServletException {
        logger.info("Item Controller Initialized");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria", req.getRequestURI());
        }
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            writer.write(String.valueOf(itemBO.saveItem(itemDTO)));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Item Saved Successfully");
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while saving Item", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var itemId = req.getParameter("itemId");
        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");

            if (itemId != null) {
                jsonb.toJson(itemBO.getItemData(itemId), writer);
                resp.setStatus(HttpServletResponse.SC_OK);
                logger.info("Item Retrieved Successfully");
            } else {
                jsonb.toJson(itemBO.getAllItems(), writer);
                resp.setStatus(HttpServletResponse.SC_OK);
                logger.info("All Items Retrieved Successfully");
            }
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while retrieving Item", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            if (itemBO.updateItem(itemDTO)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Item Updated Successfully");
            } else {
                writer.write("Item Update Failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Item Update Failed");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while updating Item", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            var itemId = req.getParameter("itemId");
            if(itemBO.deleteItem(itemId)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                logger.info("Item Deleted Successfully");
            } else {
                writer.write("Item Delete Failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                logger.error("Item Delete Failed");
            }
        } catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Error while deleting Item", e);
            e.printStackTrace();
        }
    }
}
