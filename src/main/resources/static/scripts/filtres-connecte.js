const achatsDropdown = document.getElementById("achats-dropdown"),
	ventesDropdown = document.getElementById("ventes-dropdown"),
	achatsRadio = document.getElementById("achats"),
	ventesRadio = document.getElementById("ventes"),
	achatsListe = document.getElementById("achats-liste"),
	ventesListe = document.getElementById("ventes-liste"),
	achatsCheckboxes = document.querySelectorAll(".achats"),
	ventesCheckboxes = document.querySelectorAll(".ventes");

achatsDropdown.addEventListener("mouseover", showAchats);
achatsDropdown.addEventListener("mouseout", hideAchats);
ventesDropdown.addEventListener("mouseover", showVentes);
ventesDropdown.addEventListener("mouseout", hideVentes);

achatsRadio.addEventListener("change", disableVentes);
ventesRadio.addEventListener("change", disableAchats);

function showAchats() {
	achatsListe.classList.remove("hidden");
}

function hideAchats() {
	achatsListe.classList.add("hidden");
}

function showVentes() {
	ventesListe.classList.remove("hidden");
}

function hideVentes() {
	ventesListe.classList.add("hidden");
}

function disableVentes() {
	ventesCheckboxes.forEach((item) => item.disabled = true);
	achatsCheckboxes.forEach((item) => item.disabled = false);
}

function disableAchats() {
	achatsCheckboxes.forEach((item) => item.disabled = true);
	ventesCheckboxes.forEach((item) => item.disabled = false);
}