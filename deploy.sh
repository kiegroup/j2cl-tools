bazel build //:sonatype_bundles
VERSION=0.3-SNAPSHOT

mkdir tmp
cd tmp

mkdir parent
unzip -n ../bazel-bin/closure-compiler-parent_bundle.jar -d parent
mvn deploy:deploy-file -Dfile=parent/pom.xml -DpomFile=parent/pom.xml -Durl=https://repo.vertispan.com/j2cl/ -DrepositoryId=vertispan-snapshots


mkdir main
unzip -n ../bazel-bin/closure-compiler-main_bundle.jar  -d main
mvn deploy:deploy-file -Dfile=main/pom.xml -DpomFile=main/pom.xml -Durl=https://repo.vertispan.com/j2cl/ -DrepositoryId=vertispan-snapshots


mkdir unshaded
unzip -n ../bazel-bin/closure-compiler-unshaded_bundle.jar  -d unshaded
mvn deploy:deploy-file -Dfile=unshaded/closure-compiler-unshaded-${VERSION}.jar -Djavadoc=unshaded/closure-compiler-unshaded-${VERSION}-javadoc.jar -Dsources=unshaded/closure-compiler-unshaded-${VERSION}-sources.jar -DpomFile=unshaded/pom.xml -Durl=https://repo.vertispan.com/j2cl/ -DrepositoryId=vertispan-snapshots


cd -
rm -rf tmp