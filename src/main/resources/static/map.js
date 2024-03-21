var map = L.map('map').setView([52.3676, 4.9041], 1);
L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager_labels_under/{z}/{x}/{y}{r}.png', { maxZoom: 19 }).addTo(map);
var layer;
function draw() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/geo");
    xhr.send();
    xhr.responseType = "json";
    if (layer) {
        layer.removeFrom(map)
    }
    layer = L.layerGroup()
    xhr.onload = () => {
        layer.addLayer(L.polygon(xhr.response));
        layer.addTo(map)
    };
    map.on('click', onMapClick);
}
draw();
