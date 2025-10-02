// Load cart items on page (only if cart-items table exists)
function loadCart() {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let cartList = document.getElementById("cart-items");
  let totalAmount = 0;

  if (cartList) {
    cartList.innerHTML = "";

    cart.forEach((item, index) => {
      let itemTotal = item.price * item.quantity;
      totalAmount += itemTotal;

      cartList.innerHTML += `
        <tr>
          <td><img src="${item.imageUrl}" alt="${item.name}" class="cart-item-image" width="60"></td>
          <td>${item.name}</td>
          <td>₹ ${item.price}</td>
          <td>
            <button class="btn btn-sm btn-secondary" onclick="changeQuantity('${item.id}', -1)">-</button>
            <span class="mx-2">${item.quantity}</span>
            <button class="btn btn-sm btn-secondary" onclick="changeQuantity('${item.id}', 1)">+</button>
          </td>
          <td>₹ ${itemTotal}</td>
          <td><button class="btn btn-danger btn-sm" onclick="removeFromCart('${item.id}')">x</button></td>
        </tr>
      `;
    });

    let totalEl = document.getElementById("total-amount");
    if (totalEl) {
      totalEl.innerText = "₹ " + totalAmount;
    }
  }
  updateCartCounter();
}

// Add item to cart
function addToCart(id, name, price, imageUrl) {
  price = parseFloat(price);
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let itemIndex = cart.findIndex((item) => item.id === id);

  if (itemIndex !== -1) {
    cart[itemIndex].quantity += 1;
  } else {
    cart.push({
      id: id,
      name: name,
      price: price,
      imageUrl: imageUrl,
      quantity: 1,
    });
  }

  localStorage.setItem("cart", JSON.stringify(cart));
  alert("Item added to cart");

  updateCartCounter();
  loadCart();
}

// Change quantity
function changeQuantity(id, change) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let itemIndex = cart.findIndex((item) => item.id === id);

  if (itemIndex !== -1) {
    cart[itemIndex].quantity += change;
    if (cart[itemIndex].quantity <= 0) {
      cart.splice(itemIndex, 1);
    }
    localStorage.setItem("cart", JSON.stringify(cart));
    loadCart();
  }
}

// Remove item
function removeFromCart(id) {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  cart = cart.filter((item) => item.id !== id);
  localStorage.setItem("cart", JSON.stringify(cart));
  loadCart();
}

// Update cart badge
function updateCartCounter() {
  let cart = JSON.parse(localStorage.getItem("cart")) || [];
  let totalQty = cart.reduce((sum, item) => sum + item.quantity, 0);

  let badge = document.querySelector(".cart-badge");
  if (badge) {
    badge.innerText = totalQty;
  }
}




document.addEventListener("DOMContentLoaded", () => {
  loadCart();          
  updateCartCounter(); 
});

// ------------------------------------------------------------------------------------------------
// Razorpay Integration Code
// ------------------------------------------------------------------------------------------------

// Updated function to get user data before opening the payment gateway
async function openPaymentGateway() {
  try {
     const token = localStorage.getItem('jwtToken');
    
    
    if (!token) {
      alert('Please log in to continue.');
      window.location.href = 'login.html';
      return;
    }

    // 1. Get user data from backend, JWT token ke saath
    const userResponse = await fetch('https://backend-app-6h8m.onrender.com/api/users/profile', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
    });

    
    if (userResponse.status === 401 || userResponse.status === 403) {
  alert('Your session has expired or you are not authorized. Please log in again.');
  window.location.href = 'login.html';
  return;
}
    if (!userResponse.ok) {
      throw new Error('Failed to fetch user data');
    }
    const userData = await userResponse.json();

    // 2. Get the total cart amount
    let totalAmountString = document.getElementById("total-amount").innerText;
    let totalAmount = parseFloat(totalAmountString.replace('₹ ', ''));

    // 3. Create the order on the backend
    const orderResponse = await fetch('https://backend-app-6h8m.onrender.com/api/create-order', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ amount: totalAmount })
    });

    const orderData = await orderResponse.json();

    if (orderData.id) {
      // 4. Open Razorpay with dynamic user data
      const options = {
        "key": "rzp_test_RF6hzq0v3uw8Ej",
        "amount": orderData.amount,
        "currency": "INR",
        "name": "Shoplane E-commerce",
        "description": "Payment for your order",
        "order_id": orderData.id,
        "handler": function (response) {
          verifyPayment(response);
        },
        "prefill": {
          "name": userData.name,
          "email": userData.email,
          "contact": userData.contact
        },
        "theme": {
          "color": "#3399cc"
        }
      };
      const rzp1 = new Razorpay(options);
      rzp1.open();
    } else {
      alert('Order create nahi ho paya. Dobara try karein.');
    }
  } catch (error) {
    console.error('Error in payment process:', error);
    alert('An error occurred. Please try again.');
  }
}

function verifyPayment(response) {
  fetch('https://backend-app-6h8m.onrender.com/api/verify-payment', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      razorpay_payment_id: response.razorpay_payment_id,
      razorpay_order_id: response.razorpay_order_id,
      razorpay_signature: response.razorpay_signature
    })
  })
  .then(res => res.text())
  .then(data => {
    console.log("Verification response:", data);
    if (data.includes("successful")) {
      alert("Payment Successful!");
      window.location.href = '/success.html';
    } else {
      alert("Payment Failed. Please try again.");
    }
  })
  .catch(error => {
    console.error('Error verifying payment:', error);
  });
}