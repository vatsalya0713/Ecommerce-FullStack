const BASE_URL = "https://backend-app-6h8m.onrender.com"
async function loadProducts() {
    try {
        // Fetch products from the API
    const response = await fetch(`${BASE_URL}/products`)
    const products = await response.json();
    console.log(products);

    let trendingList = document.getElementById('trending-products');
    let clothingList = document.getElementById('clothing-products');
    let electronicsList = document.getElementById('electronics-products');
    let homeAppliancesList = document.getElementById('home-appliances-products');
    let beautyList = document.getElementById('beauty-products');
    let sportsList = document.getElementById('sports-products');
    let gadgetsList = document.getElementById('gadgets-products');


    trendingList.innerHTML = "";
    clothingList.innerHTML = "";
    electronicsList.innerHTML = "";
    homeAppliancesList.innerHTML = "";
    beautyList.innerHTML = "";
    sportsList.innerHTML = "";
    gadgetsList.innerHTML = "";


   products.forEach(product => {
       let productCard = `
     <div class="col-lg-4 col-md-6">
        <div class="card h-100">
            <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">

            <div class="card-body d-flex flex-column">
                <h5 class="card-title">${product.name}</h5>
                <p class="card-text">${product.description}</p>
                <p class="price">₹ ${product.price}</p>
                <button class="btn btn-primary mt-auto"
                    onclick="addToCart('${product.id}' , '${product.name}', ${product.price}, '${product.imageUrl}')">
                    Add to Cart
                </button>
            </div>
        </div>
    </div>
`

        // Append to appropriate section
        if (product.category === "Electronics") {
            electronicsList.innerHTML += productCard;
        } else if (product.category === "Clothing") {
            clothingList.innerHTML += productCard;
        } else if (product.category === "Home Appliances") {
            homeAppliancesList.innerHTML += productCard;
        } else if (product.category === "Beauty") {
            beautyList.innerHTML += productCard;
        } else if (product.category === "Sports") {
            sportsList.innerHTML += productCard;
        } else if (product.category === "Gadgets") {
            gadgetsList.innerHTML += productCard;
        }
        else {
            trendingList.innerHTML += productCard;
        }
    });

} catch (error) {
    console.error("Error loading products:", error);
}}