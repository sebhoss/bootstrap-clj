;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See "http://www.wtfpl.net/":http://www.wtfpl.net/ for
;   more details.

(require '[com.github.sebhoss.bootstrap.repl :refer :all])

; Load project namespaces
(load-namespaces #".*?bootstrap")

; 'refresh', 'doc', 'source', etc. support
(load-helpers)
