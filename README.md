# Java Puzzle Solver

Bu proje, çeşitli arama algoritmalarını kullanarak bir kaydırmalı bulmaca oyununu çözen Java tabanlı bir uygulamadır. Kullanıcıdan veya bir dosyadan alınan başlangıç seviyesine göre çözüm yolları bulunur ve görselleştirme desteklenir.

## Özellikler

- 👣 Arama algoritmaları:
  - Breadth-First Search (BFS)
  - Depth-First Search (DFS)
  - A* Search (A-Star)
- 🎮 Oyun mantığı ve oyuncu hareketleri
- 🧩 Seviye yükleyici ve kaydedici
- 🖼️ PNG olarak çözüm adımlarını kaydetme desteği
- 📜 Basit test sınıfı ile demo çalıştırma

## Proje Yapısı

```
src/
├── AStarSearch.java
├── Board.java
├── BreadthFirstSearch.java
├── DepthFirstSearch.java
├── Directions.java
├── Game.java
├── LevelLoader.java
├── Player.java
├── PngSaver.java
├── Position.java
├── Scene.java
├── SlideResult.java
├── Solver.java
└── Test.java
```

## Başlangıç

### Gereksinimler

- Java 11 veya üzeri
- (Opsiyonel) Bir IDE (IntelliJ IDEA, Eclipse, VS Code vb.)

### Derleme ve Çalıştırma

Terminal üzerinden:

```bash
javac *.java
java Test
```

`Test.java`, temel bir seviye ile algoritmaları çalıştırır ve çıktıyı görselleştirir.

## Kullanım

- `LevelLoader.java`: Seviye bilgilerini okur
- `Solver.java`: Seçilen algoritmaya göre çözüm üretir
- `PngSaver.java`: Her adımı PNG dosyası olarak kaydeder

## Katkıda Bulunma

Pull request’ler memnuniyetle karşılanır. Lütfen büyük değişiklikler için önce ne yapmak istediğinizi tartışmak adına bir issue açın.

