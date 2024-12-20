function onClick(e) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/admin/geo', false);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify({'lat': e.latlng.lat, 'lng': e.latlng.lng}));
    xhr = new XMLHttpRequest();
    xhr.overrideMimeType('application/json');
    xhr.open('GET', 'http://localhost:8080/admin/geo', false);
    xhr.send(null);
    L.polygon(JSON.parse(xhr.responseText), {
        fillOpacity: 0,
        weight: 1,
        opacity: 1,
        color: 'red'
    }).addTo(map);
}

var adjs;

function onMouseMove(e) {
    if (adjs) {
        map.removeLayer(adjs);
    }
    var xhr = new XMLHttpRequest();
    xhr.overrideMimeType('application/json');
    xhr.open('POST', 'http://localhost:8080/admin/geo/adjs', false);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify({'lat': e.latlng.lat, 'lng': e.latlng.lng}));
    var polygon = L.polygon(JSON.parse(xhr.responseText), {
        weight: 0,
        fillOpacity: .25,
        fillColor: 'green'
    })
    adjs = L.layerGroup([polygon]);
    adjs.addTo(map);
}
