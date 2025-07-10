const track = document.getElementById('carouselTrack');
const itemsCount = track.children.length;
let index = 0;
 
setInterval(() => {
  index = (index + 1) % itemsCount;
  track.style.transform = `translateX(-${index * 100}%)`;
}, 3000);