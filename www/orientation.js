
exports.getOrientation = function (successCallback, failureCallback) {
    if (typeof successCallback != 'function') {
        throw new Error('DeviceOrientation Error: successCallback is not a function');
    }

    if (typeof failureCallback != 'function') {
        throw new Error('DeviceOrientation Error: failureCallback is not a function');
    }

    return cordova.exec(
        successCallback, failureCallback, 'DeviceOrientation', 'getOrientation', []);
};

exports.startUpdates = function (successCallback, failureCallback) {
    if (typeof successCallback != 'function') {
        throw new Error('DeviceOrientation Error: successCallback is not a function');
    }

    if (typeof failureCallback != 'function') {
        throw new Error('DeviceOrientation Error: failureCallback is not a function');
    }

    return cordova.exec(
        successCallback, failureCallback, 'DeviceOrientation', 'startUpdates', []);
};
