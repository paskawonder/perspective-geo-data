var map = L.map('map').setView([52.3676, 4.9041], 1);
L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager_labels_under/{z}/{x}/{y}{r}.png', { maxZoom: 19 }).addTo(map);
const xhr = new XMLHttpRequest();
xhr.overrideMimeType('application/json');
xhr.open("GET", "http://localhost:8080/geo", false);
xhr.send(null);
L.polygon(JSON.parse(xhr.responseText), {
    fillOpacity: 0,
    weight: 1,
    opacity: 1,
    color: 'red'
}).addTo(map);
map.on('click', onMapClick);
