function handleSubmit(event) {
  event.preventDefault();

  const name = document.getElementById("fullname").value;
  const phone = document.getElementById("mobile").value;

  const contacts = [
    document.getElementById("contact1").value,
    document.getElementById("contact2").value,
    document.getElementById("contact3").value
  ];

  // 1️⃣ CREATE USER
  fetch(`${API_BASE}/api/users`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      name: name,
      phone: phone,
      password: "1234"
    })
  })
  .then(res => res.json())
  .then(user => {

    // Save userId locally
    localStorage.setItem("userId", user.id);

    // 2️⃣ SAVE TRUSTED CONTACTS
    contacts.forEach((c, index) => {
      if (c) {
        fetch(`${API_BASE}/api/contacts/${user.id}`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            name: `Contact ${index + 1}`,
            phone: c,
            priorityOrder: index + 1
          })
        });
      }
    });

    // 3️⃣ REDIRECT TO CALCULATOR
    window.location.href = "/calc.html";
  });
}
