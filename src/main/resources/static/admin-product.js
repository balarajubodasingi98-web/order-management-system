const token = localStorage.getItem("token");
const role  = localStorage.getItem("role");

if (!token || role !== "ADMIN") {
    alert("Admin access only");
    window.location.href = "login.html";
}
function goToOrders() {
  window.location.href = "admin-orders.html";
}


// ================= LOAD PRODUCTS =================
function loadProducts() {
    fetch("/api/products/admin", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed to load products");
        return res.json();
    })
    .then(products => {
        let html = "";
        products.forEach(p => {
            html += `
                <div>
                    <b>${p.name}</b> - ₹${p.price}
                    (Stock: ${p.stock})
                    <button onclick="deleteProduct(${p.id})">Delete</button>
                </div><br>
            `;
        });
        document.getElementById("allProducts").innerHTML = html;
    })
    .catch(err => {
        console.error(err);
        alert("Error loading products");
    });
}

// ================= ADD PRODUCT =================
window.addProduct = function () {
    const name  = document.getElementById("name").value;
    const price = document.getElementById("price").value;
    const stock = document.getElementById("stock").value;

    fetch("/api/products", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({
            name: name,
            price: price,
            stock: stock
        })
    })
    .then(res => {
        if (!res.ok) throw new Error("Add failed");
        return res.json();
    })
    .then(() => {
        alert("Product added");
        loadProducts();
    })
    .catch(err => {
        console.error(err);
        alert("Failed to add product");
    });
};

// ================= DELETE PRODUCT =================
window.deleteProduct = function (id) {
    if (!confirm("Delete this product?")) return;

    fetch(`/api/products/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        if (!res.ok) throw new Error("Delete failed");
        loadProducts();
    })
    .catch(err => {
        console.error(err);
        alert("Cannot delete product");
    });
};

// ================= LOGOUT =================
window.logout = function () {
    localStorage.clear();
    window.location.href = "login.html";
};

// Load products on page load
loadProducts();
