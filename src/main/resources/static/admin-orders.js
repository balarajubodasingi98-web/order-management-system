const token = localStorage.getItem("token");

if (!token) {
  alert("Login first");
  window.location.href = "login.html";
}

fetch("/api/orders/admin", {
  headers: {
    "Authorization": "Bearer " + token
  }
})
.then(res => {
  if (!res.ok) throw new Error("Failed to load orders");
  return res.json();
})
.then(orders => {
  let html = "";
  orders.forEach(o => {
    html += `
      <div>
        <b>Order #${o.id}</b><br>
        User: ${o.user.email}<br>
        Total: ₹${o.totalAmount}<br>
        Status: ${o.status}
        <select onchange="updateStatus(${o.id}, this.value)">
          <option>CREATED</option>
          <option>SHIPPED</option>
          <option>DELIVERED</option>
        </select>
      </div><hr>
    `;
  });
  document.getElementById("orders").innerHTML = html;
});

function updateStatus(orderId, status) {
  fetch(`/api/orders/admin/${orderId}/status?status=${status}`, {
    method: "PUT",
    headers: {
      "Authorization": "Bearer " + token
    }
  }).then(() => alert("Status updated"));
}

function goToProducts() {
  window.location.href = "admin-products.html";
}

function logout() {
  localStorage.clear();
  window.location.href = "login.html";
}
