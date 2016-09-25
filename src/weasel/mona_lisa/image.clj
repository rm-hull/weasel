(ns weasel.mona-lisa.image
  (:import
    [java.awt Color Graphics2D Polygon RenderingHints]
    [java.awt.image BufferedImage]
    [javax.imageio ImageIO]))

(defn ^BufferedImage create-image [w h]
  (BufferedImage. w h BufferedImage/TYPE_INT_ARGB))

(defn ^Graphics2D create-graphics [^BufferedImage img]
  (let [g2d (.createGraphics img)]
    (doto g2d
      (.setRenderingHint RenderingHints/KEY_STROKE_CONTROL RenderingHints/VALUE_STROKE_NORMALIZE)
      (.setRenderingHint RenderingHints/KEY_ANTIALIASING RenderingHints/VALUE_ANTIALIAS_ON)
      (.setRenderingHint RenderingHints/KEY_RENDERING RenderingHints/VALUE_RENDER_QUALITY)
      (.setRenderingHint RenderingHints/KEY_INTERPOLATION RenderingHints/VALUE_INTERPOLATION_BICUBIC)
      (.setRenderingHint RenderingHints/KEY_ALPHA_INTERPOLATION RenderingHints/VALUE_ALPHA_INTERPOLATION_QUALITY)
      (.setBackground Color/BLACK)
      (.clearRect 0 0 (.getWidth img) (.getHeight img)))
    g2d))

(defn- ->polygon [points]
  (let [polygon (Polygon.)]
    (doseq [[x y] points]
      (.addPoint polygon x y))
    polygon))

(defn- ->color [[r g b a :as rgba]]
  (Color. (int r) (int g) (int b) (int a)))

(defn- draw-gene [^Graphics2D g2d gene]
  (let [{:keys [color points]} gene
        polygon (->polygon points)
        rgba (->color color)]
    (doto g2d
      (.setColor rgba)
      (.fill polygon))))

(defn draw [dna]
  (let [img (create-image 256 256)
        g2d (create-graphics img)]
    (doseq [gene dna]
      (draw-gene g2d gene))
    (.dispose g2d)
    img))

(defn diff [^BufferedImage img1 ^BufferedImage img2]
  (reduce +
    (for [y (range (.getHeight img1))
          x (range (.getWidth img1))
          :let [pixel1      (Color. (.getRGB img1 x y) true)
                pixel2      (Color. (.getRGB img2 x y) true)
                delta-red   (- (.getRed pixel1) (.getRed pixel2))
                delta-green (- (.getGreen pixel1) (.getGreen pixel2))
                delta-blue  (- (.getBlue pixel1) (.getBlue pixel2))]]
        (+ (* delta-red delta-red)
           (* delta-green delta-green)
           (* delta-blue delta-blue)))))

(defn- scale-height [^BufferedImage img new-width]
  (let [ratio (/ new-width (.getWidth img))]
    (int  (* ratio (.getHeight img)))))

(defn resize
  ([^BufferedImage img new-width]
   (resize img new-width (scale-height img new-width)))

  ([^BufferedImage img new-width new-height]
    (let [resized (create-image new-width new-height)
          g2d (create-graphics resized)]
      (.drawImage g2d img 0 0 new-width new-height 0 0 (.getWidth img) (.getHeight img) nil)
      (.dispose g2d)
      resized)))


(defn read-img [filename]
  (ImageIO/read (clojure.java.io/file filename)))

(defn write-png [^BufferedImage image filename]
  (ImageIO/write image "png" (clojure.java.io/file filename)))
