import { saveOrder } from "../model/Order.js";
import { getAllOrders } from "../model/Order.js";
import { getAllCustomers } from "../model/Customer.js";
import { getAllItems, updateItem } from "../model/Item.js";

var itemId;
var itemQty;
var orderQty;

$(document).ready(function () {
  refresh();
});

$(".orderManageBtn").click(function () {
  refresh();
});

function refresh() {
  generateOrderId().then((newOrderId) => {
    $("#OrderManage .orderId").val(newOrderId);
    $("#OrderManage .orderDate").val(new Date().toISOString().split("T")[0]);
    $("#OrderManage .Cash").val(" ");
    $("#OrderManage .Discount").val(" ");
    loadCustomer();
    loadItems();
  });
}

async function generateOrderId() {
  const orders = await getAllOrders();
  if (orders.length === 0) {
    return "OD01";
  } else {
    let lastOrder = orders[orders.length - 1];
    const lastOrderId = lastOrder ? lastOrder.id : "OD00";
    const lastIdNumber = parseInt(lastOrderId.slice(2), 10); // Convert to number
    const newIdNumber = lastIdNumber + 1;
    return "OD" + newIdNumber.toString().padStart(2, "0"); // Ensure ID is always 2 digits (minimum length)
  }
}

function loadCustomer() {
  let cmb = $("#OrderManage .customers");
  cmb.empty(); // Clear existing options

  getAllCustomers()
    .then((customers) => {
      let option = [];
      option.unshift(""); // Add an empty option at the beginning

      customers.forEach((customer) => {
        option.push(customer.id);
      });

      $.each(option, function (index, value) {
        cmb.append($("<option>").val(value).text(value));
      });
    })
    .catch((error) => {
      console.error("Failed to load data to comboBox:", error);
    });
}

function loadItems() {
  let cmb = $("#OrderManage .itemCmb");
  cmb.empty(); // Clear existing options

  getAllItems()
    .then((items) => {
      let option = [];

      items.forEach((item) => {
        option.push(item.id);
      });

      option.unshift(""); // Add an empty option at the beginning

      $.each(option, function (index, value) {
        cmb.append($("<option>").val(value).text(value));
      });
    })
    .catch((error) => {
      console.error("Failed to load data to comboBox:", error);
    });
}

$("#OrderManage .customers").change(function () {
  const selectedCustomerId = $(this).val();

  getAllCustomers()
    .then((customers) => {
      // Find the customer with the selected ID
      const customer = customers.find((c) => c.id === selectedCustomerId);

      if (customer) {
        // Populate the fields with the customer data
        $("#OrderManage .custId").val(customer.id);
        $("#OrderManage .custName").val(customer.name);
        $("#OrderManage .custAddress").val(customer.city);
        $("#OrderManage .custSalary").val(customer.email);
      } else {
        alert("Customer not found!");
      }
    })
    .catch((error) => {
      console.error("Error retrieving customers:", error);
    });
});

//.change() - A jQuery method that binds an event handler to the "change" event of the selected elements.

$("#OrderManage .itemCmb").change(function () {
  const selectedItemId = $(this).val();

  getAllItems()
    .then((items) => {
      const item = items.find((i) => i.id === selectedItemId);

      if (item) {
        $("#OrderManage .addBtn").text("Add");
        $("#OrderManage .itemCode").val(item.id);
        $("#OrderManage .itemName").val(item.name);
        $("#OrderManage .itemQty").val(item.qty);
        $("#OrderManage .itemPrice").val(item.price);
      } else {
        alert("Item not found!");
      }
    })
    .catch((error) => {
      console.error("Error retrieving items:", error);
    });
});

function clear(tableCount) {
  if (tableCount === 1) {
    $("#OrderManage .itemCode").val("");
    $("#OrderManage .itemName").val("");
    $("#OrderManage .itemPrice").val("");
    $("#OrderManage .itemQty").val("");
    $("#OrderManage .orderQty").val("");
    $("#OrderManage .SubTotal").text("");
    $("#OrderManage .Cash").val("");
    $("#OrderManage .Total").text("");
    $("#OrderManage .Discount").val("");
    $("#OrderManage .itemCmb").val("");
  } else {
    $("#OrderManage .custId").val("");
    $("#OrderManage .custName").val("");
    $("#OrderManage .custAddress").val("");
    $("#OrderManage .custSalary").val("");
    $("#OrderManage .itemCode").val("");
    $("#OrderManage .itemName").val("");
    $("#OrderManage .itemPrice").val("");
    $("#OrderManage .itemQty").val("");
    $("#OrderManage .orderQty").val("");
  }
}

let getItems = [];

$("#OrderManage .addBtn").click(function () {
  if ($("#OrderManage .addBtn").text() === "delete") {
    dropItem();
  } else {
    let getItem = {
      id: $("#OrderManage .itemCode").val(),
      name: $("#OrderManage .itemName").val(),
      price: parseFloat($("#OrderManage .itemPrice").val()),
      qty: parseInt($("#OrderManage .orderQty").val(), 10), // 10 is the radix, 10 depicts base 10
      total:
        parseFloat($("#OrderManage .itemPrice").val()) *
        parseInt($("#OrderManage .orderQty").val(), 10),
    };

    let itemQty = parseInt($("#OrderManage .itemQty").val(), 10);
    let orderQty = parseInt($("#OrderManage .orderQty").val(), 10);

    if (itemQty >= orderQty) {
      if (
        $("#OrderManage .custId").val() !== "" &&
        $("#OrderManage .custName").val() !== null
      ) {
        if (orderQty > 0) {
          let item = getItems.find((I) => I.id === getItem.id);
          if (item == null) {
            getItems.push(getItem);
            loadTable();
            clear(1);
            setTotal();
          } else {
            alert("Already Added!");
          }
        } else {
          alert("Invalid Quantity!");
        }
      } else {
        alert("Invalid Customer!");
      }
    } else {
      alert("Not Enough Quantity!");
    }
  }
});

function dropItem() {
  let itemCode = $("#OrderManage .itemCode").val();
  let item = getItems.find((I) => I.id === itemCode);
  let index = getItems.findIndex((I) => I.id === itemCode);
  getItems.splice(index, 1);
  loadTable();
  clear(1);
  setTotal();
}

function loadTable() {
  $("#OrderManage .tableRows").empty();
  for (let i = 0; i < getItems.length; i++) {
    $("#OrderManage .tableRows").append(
      "<div> " +
        "<div>" +
        getItems[i].id +
        "</div>" +
        "<div>" +
        getItems[i].name +
        "</div>" +
        "<div>" +
        getItems[i].price +
        "</div>" +
        "<div>" +
        getItems[i].qty +
        "</div>" +
        "<div>" +
        getItems[i].total +
        "</div>" +
        "</tr>"
    );
  }
}

function setTotal() {
  let total = 0;
  for (let i = 0; i < getItems.length; i++) {
    total += getItems[i].total;
  }
  $("#OrderManage .Total").text(total);
}

$("#OrderManage .placeOrder").click(function () {
  let cash = parseFloat($("#OrderManage .Cash").val());
  let total = parseFloat($("#OrderManage .Total").text());
  let discount = parseFloat($("#OrderManage .Discount").val());

  if (cash >= total) {
    if (discount >= 0 && discount <= 100) {
      let subTotal = total - (total * discount) / 100;
      $("#OrderManage .SubTotal").text(subTotal.toFixed(2));

      let balance = cash - subTotal;
      $("#OrderManage .Balance").val(balance.toFixed(2));

      let Order = {
        id: $("#OrderManage .orderId").val(),
        date: $("#OrderManage .orderDate").val(),
        customerId: $("#OrderManage .custId").val(),
        items: getItems,
        total: total,
        discount: discount,
        subTotal: subTotal,
        cash: cash,
        balance: balance,
      };

      saveOrder(Order)
        .then(() => updateItemData())
        .then(() => {
          getItems = [];
          loadTable();
          clear(2);
          refresh();
        })
        .catch((error) => {
          console.error("Failed to save order:", error);
          alert("Failed to save order");
        });
    } else {
      alert("Invalid Discount!");
    }
  } else {
    alert("Not Enough Cash!");
  }
});

function updateItemData() {
  return getAllItems()
    .then((items) => {
      const updatePromises = getItems.map((item) => {
        let foundItem = items.find((I) => I.id === item.id);
        if (foundItem) {
          foundItem.qty -= item.qty;
          return updateItem(foundItem);
        } else {
          console.warn(`Item with ID ${item.id} not found`);
          return Promise.resolve();
        }
      });
      return Promise.all(updatePromises);
    })
    .catch((error) => {
      console.error("Failed to retrieve items:", error);
      throw error;
    });
}

$(".mainTable .tableRows").on("click", "div", function () {
  let itemCode = $(this).children("div:eq(0)").text();
  let itemName = $(this).children("div:eq(1)").text();
  let price = $(this).children("div:eq(2)").text();
  let qty = $(this).children("div:eq(3)").text();

  $("#OrderManage .itemCode").val(itemCode);
  $("#OrderManage .itemName").val(itemName);
  $("#OrderManage .itemPrice").val(price);
  $("#OrderManage .orderQty").val(qty);

  $("#OrderManage .ItemSelect .addBtn").text("delete");
});
