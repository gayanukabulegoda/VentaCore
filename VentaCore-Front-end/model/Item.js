import { Items } from "../db/DB.js";

export function saveItem(item) {
  Items.push(item);
}

export function getAllItems() {
  return Items;
}

export function updateItem(index, item) {
  Items[index] = item;
}

export function deleteItem(index) {
  Items.splice(index, 1);
}
