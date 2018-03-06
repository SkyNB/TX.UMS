function objCopy(obj) {
    return JSON.parse(JSON.stringify(obj));
}

function deepCopy(source) {
    var result = {};
    for (var key in source) {
        if (typeof source[key] === 'object') {
            result[key] = deepCopy(source[key])
        } else {
            result[key] = source[key]
        }
    }
    return result;
}

function arrCopy(arr) {
    return arr.concat();
}

function arrCopy2(arr) {
    return arr.slice(0);
}