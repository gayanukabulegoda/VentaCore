import { saveItem } from "../model/Item.js";
import { getAllItems } from "../model/Item.js";
import { deleteItem } from "../model/Item.js";
import { updateItem } from "../model/Item.js";

document
  .querySelector("#ItemManage #ItemForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();
  });

$(document).ready(function () {
  refresh();
});

var itemId;
var itemName;
var itemQty;
var itemPrice;

$("#ItemManage .saveBtn").click(function () {
  itemId = $("#ItemManage .itemId").val();
  itemName = $("#ItemManage .itemName").val();
  itemQty = $("#ItemManage .itemQty").val();
  itemPrice = $("#ItemManage .itemPrice").val();

  let item = {
    id: itemId,
    name: itemName,
    qty: itemQty,
    price: itemPrice,
  };

  validate(item)
    .then((validResult) => {
      if (validResult) {
        saveItem(item, () => {
          refresh();
        });
      }
    })
    .catch((error) => {
      console.error("Validation failed:", error);
    });
});

function validate(item) {
  return new Promise((resolve, reject) => {
    let valid = true;

    if (/^I0[0-9]+$/.test(item.id)) {
      $("#ItemManage .invalidCode").text("");
      valid = true;
    } else {
      $("#ItemManage .invalidCode").text("Invalid Item Id!");
      valid = false;
    }

    if (/^(?:[A-Z][a-z]*)(?: [A-Z][a-z]*)*$/.test(item.name)) {
      $("#ItemManage .invalidName").text("");
      if (valid) {
        valid = true;
      }
    } else {
      $("#ItemManage .invalidName").text("Invalid Item Name!");
      valid = false;
    }

    if (item.qty != null && item.qty > 0) {
      $("#ItemManage .invalidQty").text("");
      if (valid) {
        valid = true;
      }
    } else {
      $("#ItemManage .invalidQty").text("Invalid Item Quantity!");
      valid = false;
    }

    if (item.price != null && item.price > 0) {
      $("#ItemManage .invalidPrice").text("");
      if (valid) {
        valid = true;
      }
    } else {
      $("#ItemManage .invalidPrice").text("Invalid Item Price!");
      valid = false;
    }

    getAllItems()
      .then((items) => {
        for (let i = 0; i < items.length; i++) {
          if (items[i].id === item.id) {
            $("#ItemManage .invalidItemId").text("Item Id Already Exists!");
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

async function generateItemId() {
  const items = await getAllItems();
  if (!items || items.length == 0) {
    return "I01";
  } else {
    let lastItem = items[items.length - 1];
    const lastItemId = lastItem ? lastItem.id : "I00";
    const lastIdNumber = parseInt(lastItemId.slice(1), 10); // Convert to number
    const newIdNumber = lastIdNumber + 1;
    return "I" + newIdNumber.toString().padStart(2, "0"); // Ensure ID is always 2 digits (minimum length)
  }
}

function refresh() {
  generateItemId().then((newItemId) => {
    $("#ItemManage .itemId").val(newItemId);
    $("#ItemManage .itemName").val("");
    $("#ItemManage .itemQty").val("");
    $("#ItemManage .itemPrice").val("");
    $("#ItemManage .invalidCode").text("");
    $("#ItemManage .invalidName").text("");
    $("#ItemManage .invalidQty").text("");
    $("#ItemManage .invalidPrice").text("");

    reloadTable();
  });
}

function reloadTable() {
  getAllItems()
    .then((items) => {
      $("#ItemManage .tableRow").empty();
      items.forEach((i) => {
        loadTable(i);
      });
    })
    .catch((error) => {
      console.error("Failed to reload table:", error);
    });
}

function loadTable(item) {
  $("#ItemManage .tableRow").append(
    "<tr> " +
      "<td>" +
      item.id +
      "</td>" +
      "<td>" +
      item.name +
      "</td>" +
      "<td>" +
      item.qty +
      "</td>" +
      "<td>" +
      item.price +
      "</td>" +
      "</tr>"
  );
}

$("#ItemManage .deleteBtn").click(function () {
  let itemId = $("#ItemManage .itemId").val();

  getAllItems().then((items) => {
    let item = items.findIndex((item) => {item.id === itemId});
    if (item) {
      deleteItem(itemId);
      refresh();
    } else {
      alert("Item Not Found!");
    }
  });
});

$("#ItemManage .updateBtn").click(function () {
  let item = {
    id: "I00",
    name: $("#ItemManage .itemName").val(),
    qty: $("#ItemManage .itemQty").val(),
    price: $("#ItemManage .itemPrice").val(),
  };

  let valid = validate(item);

  item.id = $("#ItemManage .itemId").val();

  console.log(item.id, " ======");

  if (valid) {
    getAllItems().then((items) => {
      console.log(items, " ======");
      let index = items.findIndex((i) => {i.id === item.id});
      if (index) {
        updateItem(item);
        refresh();
      } else {
        alert("Item Not Found!");
      }
    });
  }
});

$("#ItemManage .clearBtn").click(function () {
  refresh();
});

$("#ItemManage .searchBtn").click(function () {
  const itemId = $("#ItemManage .itemId").val();

  searchItem(itemId).then((item) => {
    if (item) {
      $("#ItemManage .itemName").val(item.name);
      $("#ItemManage .itemQty").val(item.qty);
      $("#ItemManage .itemPrice").val(item.price);
    } else {
      alert("Item Not Found");
    }
  })
  .catch((error) => {
    console.error("Error during search:", error);
  });
});

async function searchItem(id) {
  try {
    const items = await getAllItems();
    return items.find((i) => i.id === id);
  } catch (error) {
    console.error("Failed to get items:", error);
    return null;
  }
}

$("#ItemManage .tableRow").on("click", "tr", function () {
  let id = $(this).children("td:eq(0)").text();
  let name = $(this).children("td:eq(1)").text();
  let qty = $(this).children("td:eq(2)").text();
  let price = $(this).children("td:eq(3)").text();

  $("#ItemManage .itemId").val(id);
  $("#ItemManage .itemName").val(name);
  $("#ItemManage .itemQty").val(qty);
  $("#ItemManage .itemPrice").val(price);
});
