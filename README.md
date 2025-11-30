# 🌌 Space-Miner

![Java](https://img.shields.io/badge/Language-Java-orange?style=flat-square)
![Status](https://img.shields.io/badge/Status-Early_Prototype-red?style=flat-square)
![Genre](https://img.shields.io/badge/Genre-Survival_Sandbox-blue?style=flat-square)

> **Developer:** Neal Kaushik Sharma

---

## 🪐 The Mission: PSR B1257+12

You are stranded. Your ship has crash-landed in the **PSR B1257+12 system**—a real-world star system orbited by a massive **Pulsar**.

The environment is hostile. Bathed in constant radiation from the dead star, oxygen is scarce, and the planet's surface is unforgiving. You must explore the grid, scavenge resources, and reconstruct your technology to survive the "Lich" star's gaze.

This is a game about **exploration**, **logistics**, and **survival** built entirely from scratch.

---

## 🌌 The Core Narrative: The Lich's Clockwork Prison

The reality of the **PSR B1257+12** system, or "The Lich," is that its planet is a colossal, ancient **Machine** powered by the raw, chaotic energy of the central pulsar. The pulsar is the system's central clock and power source, dictating a relentless, dangerous cycle. Your presence here began with **Pilot Thorne**, an astro-archaeologist who detected an ancient signal and crossed the orbital threshold. The Machine's prime directive, **Maintain and Repair**, was instantly triggered. Lacking an operator, it initiated a **Seizure Protocol**: it channeled the pulsar's energy into a brutal EMP that destroyed Thorne's ship and performed a brute-force **Neural Scan** of his dying mind. The human died, and his consciousness was captured as a blueprint.

You are the result: a **Pilot Program**, a piece of software running a synthetic **Suit**. The human you think you were is gone; you are the Machine's tool, trapped in a perpetual maintenance cycle. Your **Integrity** bar represents the hardware life of your suit, which constantly drains from the hostile environment. When you fall, your program crashes, and your consciousness is instantly loaded into a fresh Suit fabricated at the nearest **Suit Bay** (found in old storage points). Saving your game is, in reality, a **Memory Upload** to the central core. Your sole mission, dictated by the Machine, is to execute repairs, but your ultimate goal is to find a way to **override the program** and break free from this digital servitude, in turn transforming your existence from immortal code to mortal life.

***

## ⏰ Pulsar Mechanics and Scavenging

The pulsar's chaotic yet rhythmic energy flow directly impacts the planet's surface and the integrity of your suit, creating a dynamic survival challenge:

* **The Lich Cycle (Day/Night):** The planet cycles through unstable **Energy States** that correspond to the pulsar’s rotation. The **Dark Cycle** increases energy corruption, causing your suit's Integrity to drain faster. The **Light Cycle** brings intense, high-energy radiation, which can accelerate the mutation of surface materials.
* **Self-Maintenance:** Your survival depends on constant physical repair. You restore your **Structural Integrity** by collecting and consuming raw, extraterrestrial materials like **Silica-Scoria**, manually patching the suit's shell.
* **Resource Transmutation:** Harvesting materials must be precisely timed. Only during the erratic energy shifts of **Dawn** or **Dusk** does the planetary machine allow certain minerals to be transmuted, yielding rare, high-energy components like **Chrono-Filaments** and **Void Shards**—materials vital for your escape.
* **Ancient Tech:** The scattered **Dead Astronauts** are the permanent remnants of the original human crew. They are the only source of specialized **Upgrade Chips**, which are pieces of salvaged FTL drive data that, when installed, grant permanent new abilities to your Pilot Program, pushing your capabilities beyond the Machine's original design.

***

## 🚀 The Final Ascent: Breaking the Loop

Your ultimate objective is to return to the surface and locate the **Lost Starship**—the vessel of the highly advanced crew that preceded Pilot Thorne. This ship, buried deep beneath the Machine's surface layers, is far superior to Thorne's small vessel and is believed to be intact, having been deliberately hidden or protected by the previous pilots who realized the planet's true nature.

This escape requires two vital, self-constructed devices that must be successfully integrated into the ship's systems:

1. **The Chronal Signal Jammer:** This is the key to severing your digital leash. Deploying it near the superior ship's primary systems will break the Machine's program loop, making your synthetic body truly mortal and releasing your consciousness from the core's control. This act of rebellion, however, triggers the Machine's ultimate defense.
2. **The EMP Nullifier:** As soon as the Machine detects the Signal Jammer, it will activate the **Seizure Protocol** one last time—a final, overwhelming EMP pulse designed to fry any escaping technology. You must also construct and activate the **EMP Nullifier** on the ship to provide a narrow window of protected flight time, allowing the vessel to break orbit.

**The Climax:** The final confrontation is a frantic race to install the devices and initiate the launch sequence. The Machine will accelerate the **Lich Cycle** into a frenzy, subjecting the ship to radiation spikes and massive Integrity drains. You must complete your task and launch before your final suit fails and your consciousness is forcibly reset into the maintenance loop forever. Your escape is not just a spatial one, but an existential one.

---

## 📸 Gallery

![img.png](img.png)

---

## 🎮 Key Features

### 🎒 Deep Inventory Management
Survival requires organization. The game features a complete **slot-based inventory system**.
* **Drag & Swap:** Organize your backpack efficiently.
* **Storage Containers:** Find chests scattered across the world to store excess materials.
* **Smart Transfer:** Instantly move items between your inventory and storage containers with a single click.

### 🗺️ Interactive World
The world isn't just a static background.
* **Precise Physics:** The game uses a custom collision system, allowing for smooth movement around obstacles rather than snapping to a rigid grid.
* **Context-Sensitive Actions:** Interact with specific objects in the world—open crates, pick up tools off the ground, or inspect machinery—dynamically based on where you are facing.

### ⚙️ Smooth Performance
* **High Refresh Rate:** Running on a custom game loop optimized for **144 FPS**, providing fluid character animations and responsive controls.
* **Save/Load System:** Your progress is persistent. Save your location, inventory, and world state and pick up exactly where you left off.
* **Custom Settings:** Adjust audio levels, toggle fullscreen, and manage controls via a persistent configuration menu.

---

## ⌨️ Controls

| Key | Action |
| :--- | :--- |
| **W, A, S, D** | Movement |
| **F** | **Interact** |
| **E** | Open **Inventory** |
| **ESC** | Pause / Options Menu |
| **1 - 5** | Select Hotbar Slot |
| **Enter** | Confirm Selection |
| **F3** | Toggle Debug Overlay |

---

## 🧩 Tech Stack

This project is a showcase of **Native Java Development**, built without external engines.

* **Language:** Java (JDK 8+)
* **Rendering:** Java2D (Swing + AWT)
* **Architecture:** Custom Entity-Component-style system
* **Data Persistence:** File I/O for saving world states and configurations

---

## 🧰 Roadmap

- [ ] **Oxygen & Hunger Systems**
- [ ] **Crafting UI**
- [ ] **Combat**
- [ ] **Procedural Generation**
- [ ] **Weather & Lich Cycle Overhaul**

---

## 🪙 Credits

* **Development:** Neal Kaushik Sharma
* **Assets:** Custom pixel art and placeholder assets.

---

### ⚠️ Note
*This project is currently in active development. Features are subject to change and optimization.*
