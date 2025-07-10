const darkButton = document.getElementById("darkmode"),
	logo = document.getElementById("logo-eni-encheres")
	returnIndex = document.getElementById("return-index"),
	body = document.body;

body.classList.add('no-transition');

darkButton.addEventListener("click", darkMode);
returnIndex.addEventListener("mouseover", logoHover);
returnIndex.addEventListener("mouseout", logoOut);

function darkMode() {
	if (body.className == "darkmode") {
		body.classList.remove("darkmode");
		localStorage.setItem("darkmode", "off");
		darkButton.innerText = "Dark";
	} else {
		body.classList.toggle("darkmode");
		localStorage.setItem("darkmode", "on");
		darkButton.innerText = "Light";
	}
}

window.addEventListener("load", checkPreference);

function checkPreference() {
	if (localStorage.getItem("darkmode") == "on") {
		body.classList.toggle("darkmode");
		darkButton.innerText = "Light";
	}

	void body.offsetWidth;

	body.classList.remove('no-transition');
}

function logoHover() {
	if (localStorage.getItem("darkmode") == "off") {
		logo.src = "/images/logo-encheres-darkgoldenrod.png";
	} else {
		logo.src = "/images/logo-encheres-gold.png";
	}
}

function logoOut() {
	logo.src = "/images/logo-encheres.png";
}