(ns toolbox.pprint
  (:require [clojure.string :as str])
  (:import java.nio.ByteBuffer))

(defn printable-ascii
  {:added "1.1.3"}
  [b]
  (let [c (char (bit-and b 0xFF))]
    (if (<= 32 (int c) 126) c \.)))

(defn ^:private hexdump-line
  {:added "1.1.3"}
  [offset bytes]
  (let [hex-part (->> (map #(format "%02X" (bit-and % 0xFF)) bytes)
                      (str/join " "))
        ascii-part (->> (map printable-ascii bytes)
                        (apply str))
        pad (apply str (repeat (* 3 (- 16 (count bytes))) " "))]
    (format "%08X  %s%s  |%s|" offset hex-part pad ascii-part)))

(defn bytebuffer->bytes
  "Convert `ByteBuffer` to array of `bytes`."
  {:added "1.1.3"}
  ^bytes [^ByteBuffer buf]
  (let [dup (.duplicate buf)
        arr (byte-array (.remaining dup))]
    (.get dup arr)
    arr))

(defn hexdump-bytebuffer
  "Print in an `hexdump` like format `ByteBuffer` to STDOUT"
  {:added "1.1.3"}
  ([^ByteBuffer buf]
   (hexdump-bytebuffer buf 16))
  ([^ByteBuffer buf pad]
   (println (apply str (repeatedly 77 (constantly "="))))
   (let [bs (seq (bytebuffer->bytes buf))]
     (doseq [[idx chunk] (map-indexed vector (partition-all pad bs))]
       (println (hexdump-line (* idx pad) chunk))))))
