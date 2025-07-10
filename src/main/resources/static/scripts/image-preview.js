const imageInput = document.getElementById("fileUpload"),
	imageContainer = document.getElementById("img-preview");

imageInput.addEventListener("change", showImage);

function showImage() {
	const files = imageInput.files[0];
	if (files) {
		const fileReader = new FileReader();
		fileReader.readAsDataURL(files);
		fileReader.addEventListener("load", function() {
			imageContainer.style.display = "block";
			imageContainer.innerHTML = '<img src="' + this.result + '" />';
		});
	}
}