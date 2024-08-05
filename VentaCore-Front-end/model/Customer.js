export function saveCustomer(customer, callback) {
  const customerJSON = JSON.stringify(customer);
  const http = new XMLHttpRequest();
  http.onreadystatechange = () => {
    if (http.readyState === 4) {
      if (http.status === 200 || http.status === 201) {
        console.log(JSON.stringify(http.responseText));
        if (callback) callback();
      } else {
        console.error("Customer Save Unsuccessful");
        console.error("Status", http.status);
        console.error("Ready State", http.readyState);
      }
    } else {
      console.error("Ready State", http.readyState);
    }
  };
  http.open("POST", "http://localhost:8080/ventacore/customer", true);
  http.setRequestHeader("Content-Type", "application/json");
  http.send(customerJSON);
}

export function getAllCustomers() {
  return new Promise((resolve, reject) => {
    const http = new XMLHttpRequest();
    http.onreadystatechange = () => {
      if (http.readyState === 4) {
        if (http.status === 200) {
          const customers = JSON.parse(http.responseText);
          resolve(customers);
        } else {
          reject("Failed to retrieve customers");
        }
      }
    };
    http.open("GET", "http://localhost:8080/ventacore/customer", true);
    http.send();
  });
}

export function updateCustomer(customer) {
  const customerJSON = JSON.stringify(customer);
  const http = new XMLHttpRequest();
  http.onreadystatechange = () => {
    if (http.readyState === 4) {
      if (http.status === 204) {
        console.log("Customer updated successfully");
      } else {
        console.error("Customer Update Unsuccessful");
        console.error("Status", http.status);
        console.error("Ready State", http.readyState);
      }
    } else {
      console.error("Ready State", http.readyState);
    }
  };
  http.open("PUT", "http://localhost:8080/ventacore/customer", true);
  http.setRequestHeader("Content-Type", "application/json");
  http.send(customerJSON);
}

export function deleteCustomer(customerId) {
  const http = new XMLHttpRequest();
  http.onreadystatechange = () => {
    if (http.readyState === 4) {
      if (http.status === 204) {
        console.log("Customer deleted successfully");
      } else {
        console.error("Customer Delete Unsuccessful");
        console.error("Status", http.status);
        console.error("Ready State", http.readyState);
      }
    } else {
      console.error("Ready State", http.readyState);
    }
  };
  http.open(
    "DELETE",
    `http://localhost:8080/ventacore/customer?customerId=${customerId}`,
    true
  );
  http.send();
}
