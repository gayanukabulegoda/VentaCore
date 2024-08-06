import { saveCustomer } from "../model/Customer.js";
import { getAllCustomers } from "../model/Customer.js";
import { updateCustomer } from "../model/Customer.js";
import { deleteCustomer } from "../model/Customer.js";

$(document).ready(function () {
  refresh();
});

document
  .querySelector("#CustomerManage #customerForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();
  });

var custId;
var custName;
var custEmail;
var custCity;

$("#CustomerManage .saveBtn").click(function () {
  custId = $("#CustomerManage .custId").val();
  custName = $("#CustomerManage .custName").val();
  custEmail = $("#CustomerManage .custEmail").val();
  custCity = $("#CustomerManage .custCity").val();

  let customer = {
    id: custId,
    name: custName,
    email: custEmail,
    city: custCity,
  };

  validate(customer)
    .then((validResult) => {
      if (validResult) {
        saveCustomer(customer, () => {
          refresh();
        });
      }
    })
    .catch((error) => {
      console.error("Validation failed:", error);
    });
});

function validate(customer) {
  return new Promise((resolve, reject) => {
    let valid = true;

    if (/^C[0-9]+$/.test(customer.id)) {
      $("#CustomerManage .invalidCustId").text("");
      valid = true;
    } else {
      $("#CustomerManage .invalidCustId").text("Invalid Customer Id!");
      valid = false;
    }

    if (/^(?:[A-Z][a-z]*)(?: [A-Z][a-z]*)*$/.test(customer.name)) {
      $("#CustomerManage .invalidCustName").text("");

      if (valid) {
        valid = true;
      }
    } else {
      $("#CustomerManage .invalidCustName").text("Invalid Customer Name!");
      valid = false;
    }

    if (/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(customer.email)) {
      $("#CustomerManage .invalidCustEmail").text("");

      if (valid) {
        valid = true;
      }
    } else {
      $("#CustomerManage .invalidCustEmail").text("Invalid Email Address!");
      valid = false;
    }

    if (/^(?:[A-Z][a-z]*)(?: [A-Z][a-z]*)*$/.test(customer.city)) {
      $("#CustomerManage .invalidCustCity").text("");
      if (valid) {
        valid = true;
      }
    } else {
      $("#CustomerManage .invalidCustCity").text("Invalid City Name!");
      valid = false;
    }

    getAllCustomers()
      .then((customers) => {
        for (let i = 0; i < customers.length; i++) {
          if (customers[i].id === customer.id) {
            $("#CustomerManage .invalidCustId").text(
              "Customer Id Already Exists!"
            );
            valid = false;
          }
        }
        resolve(valid);
      })
      .catch((error) => {
        console.error(error);
        reject(false);
      });
  });
}

async function createCustomerId() {
  const customers = await getAllCustomers();
  if (!customers || customers.length === 0) {
    return "C01";
  } else {
    const lastCustomer = customers[customers.length - 1];
    const lastCustomerId = lastCustomer ? lastCustomer.id : "C00";
    const lastIdNumber = parseInt(lastCustomerId.slice(1), 10); // Convert to number
    const newIdNumber = lastIdNumber + 1;
    return "C" + newIdNumber.toString().padStart(2, "0"); // Ensure ID is always 2 digits (minimum length)
  }
}

$("#CustomerManage .clearBtn").click(function () {
  refresh();
});

function refresh() {
  createCustomerId().then((newCustomerId) => {
    $("#CustomerManage .custId").val(newCustomerId);
    $("#CustomerManage .custName").val("");
    $("#CustomerManage .custEmail").val("");
    $("#CustomerManage .custCity").val("");
    $("#CustomerManage .invalidCustId").text("");
    $("#CustomerManage .invalidCustName").text("");
    $("#CustomerManage .invalidCustEmail").text("");
    $("#CustomerManage .invalidCustCity").text("");

    reloadTable();
  });
}

function reloadTable() {
  getAllCustomers()
    .then((customers) => {
      $("#CustomerManage .tableRow").empty();
      customers.forEach((c) => {
        loadTable(c);
      });
    })
    .catch((error) => {
      console.error("Failed to reload table:", error);
    });
}

function loadTable(customer) {
  $("#CustomerManage .tableRow").append(
    "<tr> " +
      "<td>" +
      customer.id +
      "</td>" +
      "<td>" +
      customer.name +
      "</td>" +
      "<td>" +
      customer.email +
      "</td>" +
      "<td>" +
      customer.city +
      "</td>" +
      "</tr>"
  );
}

$("#CustomerManage .searchBtn").click(function () {
  const custId = $("#CustomerManage .custId").val();

  searchCustomer(custId)
    .then((customer) => {
      if (customer) {
        $("#CustomerManage .custName").val(customer.name);
        $("#CustomerManage .custEmail").val(customer.email);
        $("#CustomerManage .custCity").val(customer.city);
      } else {
        alert("Customer Not Found");
      }
    })
    .catch((error) => {
      console.error("Error during search:", error);
    });
});

async function searchCustomer(id) {
  try {
    const customers = await getAllCustomers();
    return customers.find((c) => c.id === id);
  } catch (error) {
    console.error("Failed to get customers:", error);
    return null;
  }
}

$("#CustomerManage .updateBtn").click(function () {
  let UpdateCustomer = {
    id: "C00",
    name: $("#CustomerManage .custName").val(),
    email: $("#CustomerManage .custEmail").val(),
    city: $("#CustomerManage .custCity").val(),
  };

  let validResult = validate(UpdateCustomer);

  UpdateCustomer.id = $("#CustomerManage .custId").val();

  if (validResult) {
    getAllCustomers()
      .then((customers) => {
        let customer = customers.findIndex((c) => {c.id === UpdateCustomer.id});
        if (customer) {
          updateCustomer(UpdateCustomer);
          refresh();
        } else {
          alert("Customer Not Found!");
        }
      })
      .catch((error) => {
        console.error("Failed to get customers:", error);
      });
  }
});

$("#CustomerManage .removeBtn").click(function () {
  let custId = $("#CustomerManage .custId").val();

  getAllCustomers()
    .then((customers) => {
      let customer = customers.findIndex((c) => {c.id === custId});
      if (customer) {
        deleteCustomer(custId);
        refresh();
      } else {
        alert("Customer Not Found!");
      }
    })
    .catch((error) => {
      console.error("Failed to get customers:", error);
    });
});

$("#CustomerManage .tableRow").on("click", "tr", function () {
  let id = $(this).children("td:eq(0)").text();
  let name = $(this).children("td:eq(1)").text();
  let email = $(this).children("td:eq(2)").text();
  let city = $(this).children("td:eq(3)").text();

  $("#CustomerManage .custId").val(id);
  $("#CustomerManage .custName").val(name);
  $("#CustomerManage .custEmail").val(email);
  $("#CustomerManage .custCity").val(city);
});
