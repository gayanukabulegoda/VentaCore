export function saveOrder(order) {
  return new Promise((resolve, reject) => {
    const orderJSON = JSON.stringify(order);
    const http = new XMLHttpRequest();
    http.onreadystatechange = () => {
      if (http.readyState === 4) {
        if (http.status === 200 || http.status === 201) {
          console.log(JSON.stringify(http.responseText));
          resolve(http.responseText);
        } else {
          console.error("Order Save Unsuccessful");
          console.error("Status", http.status);
          console.error("Ready State", http.readyState);
          reject(new Error("Failed to save order"));
        }
      } else {
        console.error("Ready State", http.readyState);
      }
    };
    http.open("POST", "http://localhost:8080/ventacore/order", true);
    http.setRequestHeader("Content-Type", "application/json");
    http.send(orderJSON);
  });
}

export function getAllOrders() {
  return new Promise((resolve, reject) => {
    const http = new XMLHttpRequest();
    http.onreadystatechange = () => {
      if (http.readyState === 4) {
        if (http.status === 200) {
          const orders = JSON.parse(http.responseText);
          resolve(orders);
        } else {
          reject("Failed to retrieve orders");
        }
      }
    };
    http.open("GET", "http://localhost:8080/ventacore/order", true);
    http.send();
  });
}
