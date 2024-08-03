import { Orders } from "../db/DB.js";

export function saveOrder(order) {
  Orders.push(order);
}

export function getAllOrders() {
  return Orders;
}
