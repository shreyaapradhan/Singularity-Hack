let input = "";
let sosId = null;

// Redirect if user not registered
window.onload = () => {
  if (!localStorage.getItem("userId")) {
    window.location.href = "/index.html";
  }
};

function press(v) {
  input += v;
  document.getElementById("display").value = input;
}

function clearDisplay() {
  input = "";
  document.getElementById("display").value = "";
}

function calculate() {

  if (input === SECRET_CODE) {
    triggerSOS();
    document.getElementById("display").value = "19";
    input = "";
    return;
  }

  try {
    document.getElementById("display").value = eval(input);
    input = "";
  } catch {
    clearDisplay();
  }
}

// ===============================
// 🚨 TRIGGER SOS
// ===============================
function triggerSOS() {

  const userId = localStorage.getItem("userId");

  fetch(`${API_BASE}/api/sos/trigger/${userId}`, {
    method: "POST"
  })
  .then(res => res.json())
  .then(sos => {

    sosId = sos.id;
    startLocationTracking();
    showCheckInButton();

    console.log("🚨 SOS TRIGGERED");
  });
}

// ===============================
// 📍 LIVE GPS TRACKING
// ===============================
function startLocationTracking() {

  setInterval(() => {
    navigator.geolocation.getCurrentPosition(pos => {

      fetch(`${API_BASE}/api/location/${sosId}?lat=${pos.coords.latitude}&lng=${pos.coords.longitude}`, {
        method: "POST"
      });

    });
  }, LOCATION_INTERVAL);
}

// ===============================
// ✅ CHECK-IN BUTTON
// ===============================
function showCheckInButton() {

  const btn = document.createElement("button");
  btn.innerText = "I am Safe";
  btn.style.position = "fixed";
  btn.style.bottom = "20px";
  btn.style.right = "20px";

  btn.onclick = () => {
    fetch(`${API_BASE}/api/risk/check-in/1`, { method: "POST" })
      .then(() => alert("You are marked safe"));
  };

  document.body.appendChild(btn);
}
