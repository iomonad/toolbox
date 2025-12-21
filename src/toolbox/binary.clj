(ns toolbox.binary
  (:require [clojure.spec.alpha :as spec]
            [toolbox.pprint :refer [hexdump-bytebuffer]])
  (:import java.nio.ByteBuffer
           java.nio.HeapByteBuffer
           java.nio.channels.FileChannel
           java.nio.file.Paths
           java.nio.file.StandardOpenOption
           java.io.File))

(spec/def ::buffer-size int?)

(defn bbuf-allocate
  "Allocate a `ByteBuffer` with fixed size of `n`"
  {:added "1.1.3"}
  [n]
  {:pre [(spec/valid? ::buffer-size n)]}
  (ByteBuffer/allocate n))

(defn bbuf-allocate-direct
  "Allocate a `ByteBuffer` with fixed size of `n`"
  {:added "1.1.3"}
  [n]
  (ByteBuffer/allocateDirect n))

(defn bbuf-wrap
  "Wraps a byte array into a buffer.

  Param array: The array that will back the new buffer

  Param offset: The offset of the subarray to be used; must be non-negative and
  no larger than array.length.  The new buffer's position
  will be set to this value.

  Param length: The length of the subarray to be used;
  must be non-negative and no larger than
  array.length - offset.
  The new buffer's limit will be set to offset + length."
  {:added "1.1.3"}
  ([bytes offset length]
   (ByteBuffer/wrap bytes offset length))
  ([bytes]
   (ByteBuffer/wrap bytes)))

;;; Basic

(defn bbuf-capacity
  "Retrieve the `ByteBuffer` capacity size"
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.capacity buff))

(defn bbuf-position
  "Retrieve the `ByteBuffer` position"
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.position buff))

(defn bbuf-set-position
  "Set the `ByteBuffer` new position.
   If the mark is defined and larger than the new position then it
   is discarded."
  {:added "1.1.3"}
  [^ByteBuffer buff pos]
  (.position buff pos))

(defn bbuf-limit
  "Retrieve the `ByteBuffer` limit"
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.limit buff))

(defn bbuf-set-limit
  "Sets the `ByteBuffer` limit.

   If the position is larger than the new limit then it is set to the new limit.
   If the mark is defined and larger than the new limit then it is discarded."
  {:added "1.1.3"}
  [^ByteBuffer buff limit]
  (.limit buff limit))

(defn bbuf-mark!
  "Sets this buffer's mark at its position."
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.mark buff))

(defn bbuf-reset!
  "Resets this buffer's position to the previously-marked position. "
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.reset buff))

(defn bbuf-clear
  "Clears this buffer. The position is set to zero, the limit is set to the capacity,
   and the mark is discarded.

  This method does not actually erase the data in the buffer, but it is named as
  if it did because it will most often be used in situations in which that might as well be the case. "
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.clear buff))

(defn bbuf-flip
  "Flips this buffer. The limit is set to the current position and then the position is set to zero.

  If the mark is defined then it is discarded.
  This method is often used in conjunction with the compact method when transferring data from one place to another."
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.flip buff))

(defn bbuf-rewind
  "Rewinds this buffer. The position is set to zero and the mark is discarded.

  Invoke this method before a sequence of channel-write or get operations,
  assuming that the limit has already been set appropriately."
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.rewind buff))

(defn bbuf-remaining
  "Returns the number of elements between the current position and the limit."
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.remaining buff))

(defn bbuf-readonly-copy
  "Creates a new, read-only byte buffer that shares this buffer's
  content."
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.asReadOnlyBuffer buff))

(defn bbuf-hexdump
  "Print in an `hexdump` like format `ByteBuffer` to STDOUT"
  {:added "1.1.3"}
  [^HeapByteBuffer buffer]
  (hexdump-bytebuffer buffer))

(defn bbuf-spit
  "Spit `ByteBuffer` content to file"
  {:added "1.1.3"}
  [^File file ^ByteBuffer buff]
  (with-open [channel (FileChannel/open
                       (Paths/get file (into-array String []))
                       (into-array StandardOpenOption
                                   [StandardOpenOption/CREATE
                                    StandardOpenOption/WRITE]))]
    (.write channel buff)))

(defn direct?
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.isDirect buff))

(defn remaining?
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.hasRemaining buff))

(defn readOnly?
  {:added "1.1.3"}
  [^ByteBuffer buff]
  (.isReadOnly buff))
