(require '[com.github.sebhoss.bootstrap.repl :refer :all])

; Load project namespaces
(load-namespaces #".*?bootstrap")

; 'refresh', 'doc', 'source', etc. support
(load-helpers)
