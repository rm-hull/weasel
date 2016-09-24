(defproject rm-hull/weasel "0.1.0"
  :description "Demonstration of the process that drives evolutionary systems"
  :url "https://github.com/rm-hull/weasel"
  :license {
    :name "The MIT License (MIT)"
    :url "http://opensource.org/licenses/MIT"}
  :dependencies []
  :scm {:url "git@github.com:rm-hull/weasel.git"}
  :vcs :git
  :source-paths ["src"]
  :jar-exclusions [#"(?:^|/).git"]
  :codox {
    :source-paths ["src"]
    :output-path "doc/api"
    :source-uri "http://github.com/rm-hull/weasel/blob/master/{filepath}#L{line}"  }
  :min-lein-version "2.6.1"
  :profiles {
    :dev {
      :global-vars {*warn-on-reflection* true}
      :plugins [
        [lein-codox "0.9.5"]
        [lein-cloverage "1.0.6"]]
      :dependencies [
        [org.clojure/clojure "1.8.0"]]}})

