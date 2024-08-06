export function saveItem(item, callback) {
  const itemJSON = JSON.stringify(item);
  const http = new XMLHttpRequest();
  http.onreadystatechange = () => {
    if (http.readyState === 4) {
      if (http.status === 200 || http.status === 201) {
        console.log(JSON.stringify(http.responseText));
        if (callback) callback();
      } else {
        console.error("Item Save Unsuccessful");
        console.error("Status", http.status);
        console.error("Ready State", http.readyState);
      }
    } else {
      console.error("Ready State", http.readyState);
    }
  };
  http.open("POST", "http://localhost:8080/ventacore/item", true);
  http.setRequestHeader("Content-Type", "application/json");
  http.send(itemJSON);
}

export function getAllItems() {
  return new Promise((resolve, reject) => {
    const http = new XMLHttpRequest();
    http.onreadystatechange = () => {
      if (http.readyState === 4) {
        if (http.status === 200) {
          const items = JSON.parse(http.responseText);
          resolve(items);
        } else {
          reject("Failed to retrieve items");
        }
      }
    };
    http.open("GET", "http://localhost:8080/ventacore/item", true);
    http.send();
  });
}

export function updateItem(item) {
  const itemJSON = JSON.stringify(item);
  const http = new XMLHttpRequest();
  http.onreadystatechange = () => {
    if (http.readyState === 4) {
      if (http.status === 204) {
        console.log("Item updated successfully");
      } else {
        console.error("Item Update Unsuccessful");
        console.error("Status", http.status);
        console.error("Ready State", http.readyState);
      }
    } else {
      console.error("Ready State", http.readyState);
    }
  };
  http.open("PUT", "http://localhost:8080/ventacore/item", true);
  http.setRequestHeader("Content-Type", "application/json");
  http.send(itemJSON);
}

export function deleteItem(itemId) {
  const http = new XMLHttpRequest();
  http.onreadystatechange = () => {
    if (http.readyState === 4) {
      if (http.status === 204) {
        console.log("Item deleted successfully");
      } else {
        console.error("Item Delete Unsuccessful");
        console.error("Status", http.status);
        console.error("Ready State", http.readyState);
      }
    } else {
      console.error("Ready State", http.readyState);
    }
  };
  http.open(
    "DELETE",
    `http://localhost:8080/ventacore/item?itemId=${itemId}`,
    true
  );
  http.send();
}
