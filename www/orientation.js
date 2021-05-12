
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

exports.startAccelerometerUpdates = function (successCallback, failureCallback) {
    if (typeof successCallback != 'function') {
        throw new Error('DeviceOrientation Error: successCallback is not a function');
    }

    if (typeof failureCallback != 'function') {
        throw new Error('DeviceOrientation Error: failureCallback is not a function');
    }

    return cordova.exec(
        successCallback, failureCallback, 'DeviceOrientation', 'startAccelerometerUpdates', []);
};

exports.stopAccelerometerUpdates = function (successCallback, failureCallback) {
    if (typeof successCallback != 'function') {
        throw new Error('DeviceOrientation Error: successCallback is not a function');
    }

    if (typeof failureCallback != 'function') {
        throw new Error('DeviceOrientation Error: failureCallback is not a function');
    }

    return cordova.exec(
        successCallback, failureCallback, 'DeviceOrientation', 'stopAccelerometerUpdates', []);
};
