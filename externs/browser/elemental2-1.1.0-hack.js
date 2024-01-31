/**
 * @fileoverview Describes types present in elemental2 1.1.0 that are not in closure-compiler, since they aren't
 * real browser types. Presently this only contains the `WebWorker` type which never existed as a browser type,
 * but showed up in closure-compiler as a typo or early draft or something. As long as elemental2 1.1.0 is
 * supported, we must keep this file in our closure-compiler fork.
 *
 * TODO Remove this once elemental2 1.1.0 is considered to be unsupported by j2cl - this day may not come soon,
 * if ever, as it is a "stable" release.
 *
 * @externs
 */

/**
 * @see http://dev.w3.org/html5/workers/
 * @constructor
 * @implements {EventTarget}
 */
function WebWorker() {}

/** @override */
WebWorker.prototype.addEventListener = function(type, listener, opt_options) {};

/** @override */
WebWorker.prototype.removeEventListener = function(
    type, listener, opt_options) {};

/** @override */
WebWorker.prototype.dispatchEvent = function(evt) {};

/**
 * Stops the worker process
 * @return {undefined}
 */
WebWorker.prototype.terminate = function() {};

/**
 * Posts a message to the worker thread.
 * @param {string} message
 * @return {undefined}
 */
WebWorker.prototype.postMessage = function(message) {};

/**
 * Sent when the worker thread posts a message to its creator.
 * @type {?function(!MessageEvent<*>): void}
 */
WebWorker.prototype.onmessage;

/**
 * Sent when the worker thread encounters an error.
 * @type {?function(!ErrorEvent): void}
 */
WebWorker.prototype.onerror;

