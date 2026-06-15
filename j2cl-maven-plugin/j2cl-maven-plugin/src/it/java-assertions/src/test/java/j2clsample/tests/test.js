goog.module('expect.assertions');

const jre = goog.require('jre');

/** @define {string} */
const armed = goog.define('expect.assertions.armed', 'false');

jre.addSystemPropertyFromGoogDefine('expect.assertions.armed', armed);

exports = {
    armed,
};