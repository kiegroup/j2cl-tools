# [J2CL](http://j2cl.io)  &middot; [![Build Status](https://github.com/google/j2cl/actions/workflows/ci.yaml/badge.svg)](https://github.com/google/j2cl/actions/workflows/ci.yaml)

Seamless Java in JavaScript applications that tightly optimizes with
[Closure Compiler](https://github.com/google/closure-compiler)


---

This is Vertispan LLC's fork of J2CL, which includes a few changes from Google's
original repository including

* Create Maven pom.xml files to use and distribute J2CL without using Bazel.
* Make some internal APIs public, to more easily build a Maven plugin with the
  same flexibility as the Bazel rules.
* Allow more than one J2CL test suite to be built in a single compile.
* Modify Bazel wiring to allow GWT 2's GWTTestCase to be usable outside J2CL
  itself.
* Modify Bazel wiring to build Javadocs

As these changes alter J2CL's own public API, we are currently versioning releases
and snapshots as 0.x.y-<hash> releases, to indicate first that this has not yet
reached a 1.0 build, second that each time the `x` increments some API has likely
changed and downstream users should pay attention to what might have been modified,
and third to point at the upstream `<hash>` commit sha from the https://github.com/google/j2cl
repository to see where our own changes were applied. The `y` exists only to make
it clear what order the releases were in - two release with different `y` versions
could be packaging changes (if the commit hash is the same) or could be
api-compatible with each other but still worth an update.

The release process is to rebase this set of commits, then update the version in
the poms appropriately. When a release is pushed to Maven central, a tag will
be pushed to our git repository with a matching version. Snapshot builds on the
`rebased_master` branch will use `-SNAPSHOT` instead of a commit hash, and will be
force pushed as the commits are rebased and updated.

---
J2CL is a powerful, simple and lightweight transpiler from Java to Closure style
JavaScript.

* **Get the best out of Java and JavaScript.** You no longer need to choose between
the two or lock into a specific framework or a language. Choose the right language
at the right place and hire the best talent for the job.

* **Get it correct the first time.** The robust run-time type checking based on
the strong Java type system combined with the advanced cross language type checks
catches your mistakes early on.

* **Provides massive code reuse.** J2CL closely follows the Java language
[semantics](docs/limitations.md). This reduces surprises, enables reuse across
different platforms and brings most popular Java libraries into your toolkit
including [Guava](https://github.com/google/guava), [Dagger](https://dagger.dev/)
and [AutoValue](https://github.com/google/auto/tree/master/value).

* **Modern, fresh and blazing fast.** Powered by [Bazel](https://bazel.build/),
J2CL provides a fast and modern development experience that will make you smile
and keep you productive.

* **Road tested and trusted.** J2CL is the underlying technology of the most
advanced GSuite apps developed by Google including GMail, Inbox, Docs, Slides
and Calendar.


Guides
------
- [Getting Started](docs/getting-started.md)
- [JsInterop Cookbook](docs/jsinterop-by-example.md)
- [J2CL Best Practices](docs/best-practices.md)
- [Emulation Limitations](docs/limitations.md)
- [Bazel Tutorial](https://docs.bazel.build/versions/master/tutorial/java.html)
- [Bazel Best Practices](https://docs.bazel.build/versions/master/best-practices.html)


Get Support
------
- Please subscribe to [J2CL announce](http://groups.google.com/forum/#!forum/j2cl-announce) for announcements (low traffic).
- Please report [bugs](https://github.com/google/j2cl/issues/new?template=bug_report.md&labels=bug)
or file [feature requests](https://github.com/google/j2cl/issues/new?template=feature_request.md&labels=enhancement)
via [issue tracker](https://github.com/google/j2cl/issues).
- For other questions please use the [discussions](https://github.com/google/j2cl/discussions/new?category=q-a).


Caveat Emptor
-------------
J2CL is production ready and actively used by many of Google's products, but the
process of adapting workflows and tooling for the open-source version is not yet
finalized.

Wasm support is fully working but should be considered as work-in-progress since
the related parts of the spec is not finalized yet. We are working closely with
W3C and V8 team to finalize it and make it available in all browsers.

You can see what we are working on [here](https://github.com/google/j2cl/issues/93).

Last, the workflow is **not** yet supported in Windows. You can contribute to
make this a reality. Coordinate and follow the progress of this effort
[here](https://github.com/google/j2cl/issues/9).
For developers that want to use Windows as their platform we recommend
installing under WSL (Windows Subsystem for Linux).

Stay tuned!


J2CL vs. GWT?
---
In early 2015, Google GWT team made a difficult but necessary decision to work
on a new alternative product to enable Java for the Web.

It was mostly due to changing trends in the web ecosystem and our new internal
customers who were looking at Java on the Web not as an isolated ecosystem but
an integral part of their larger stack. It required a completely new vision
to create tools from the ground up, that are tightly integrated with the rest of
the ecosystem. A modern architecture, that is reliable, fast and provides a
quick iteration cycle.

There was no practical way to achieve those goals completely incrementally out
of GWT. We started from scratch using everything we learned from working on GWT
over the years. In the meantime, we kept GWT steering committee members in the
loop and gave contributors very early access so they could decide to build the
next version of GWT on J2CL.

The strategy has now evolved GWT3 to an SDK focused on libraries and enterprise
tooling which was one of the strongest points of GWT all along.

We think that such separation of concerns is crucial part of the success of the
both projects and will provide the best results for the open source community.


Contributing
------------
Read how to [contribute to J2CL](CONTRIBUTING.md).

Licensing
---------
Please refer to [the license file](LICENSE).

Disclaimers
-----------
J2CL is not an official Google product and is currently in 'alpha' release for developer preview.
