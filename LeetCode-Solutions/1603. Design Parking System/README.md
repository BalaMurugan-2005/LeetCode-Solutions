# [1603. Design Parking System](https://leetcode.com/problems/design-parking-system/)

## Problem Statement

Design a parking system for a parking lot. The parking lot has three kinds of parking spaces: big, medium, and small, with a fixed number of slots for each size.

Implement the `ParkingSystem` class:

- `ParkingSystem(int big, int medium, int small)` Initializes object of the `ParkingSystem` class. The number of slots for each parking space are given as part of the constructor.
- `bool addCar(int carType)` Checks whether there is a parking space of `carType` for the car that wants to get into the parking lot. `carType` can be of three kinds: big, medium, or small, which are represented by `1`, `2`, and `3` respectively. **A car can only park in a parking space of its `carType`**. If there is no space available, return `false`, else park the car in that size space and return `true`.

### Example

```
Input
["ParkingSystem", "addCar", "addCar", "addCar", "addCar"]
[[1, 1, 0], [1], [2], [3], [1]]
Output
[null, true, true, false, false]

Explanation
ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
parkingSystem.addCar(1); // return true because there is 1 available slot for a big car
parkingSystem.addCar(2); // return true because there is 1 available slot for a medium car
parkingSystem.addCar(3); // return false because there is no available slot for a small car
parkingSystem.addCar(1); // return false because there is no available slot for a big car. It is already occupied.
```

### Constraints

- `0 <= big, medium, small <= 1000`
- `carType` is `1`, `2`, or `3`
- At most `1000` calls will be made to `addCar`

---

## Approach: Keep a Counter for Each Slot Type

Each car type can only use its own kind of slot, and the only thing to track is **how many free slots are left** for each type. So the class stores three integers: `big`, `medium` and `small`.

1. **Constructor:** save the number of slots for each size in the fields `big`, `medium` and `small`.
2. **`addCar(carType)`:** pick the counter that matches the car type:
   - `carType == 1` uses `big`.
   - `carType == 2` uses `medium`.
   - otherwise (`carType == 3`) uses `small`.
3. For the chosen counter:
   - If it is `0`, there is no free slot, so return `false`.
   - Otherwise decrease it by `1` (a car takes one slot) and return `true`.

### Dry Run

`new ParkingSystem(1, 1, 0)`, so `big = 1`, `medium = 1`, `small = 0`

| Call | Counter checked | Free slots | Action | Returns | Counters after |
|------|-----------------|------------|--------|---------|----------------|
| `addCar(1)` | big | 1 | take a slot | `true` | big = 0, medium = 1, small = 0 |
| `addCar(2)` | medium | 1 | take a slot | `true` | big = 0, medium = 0, small = 0 |
| `addCar(3)` | small | 0 | no slot | `false` | no change |
| `addCar(1)` | big | 0 | no slot | `false` | no change |

Output: `[null, true, true, false, false]`

---

## Solution (Java)

```java
class ParkingSystem {

    // number of free slots for each car type
    int big, medium, small;

    public ParkingSystem(int big, int medium, int small) {
        this.big = big;
        this.medium = medium;
        this.small = small;
    }

    public boolean addCar(int carType) {

        if (carType == 1) {
            if (big == 0) {
                return false;
            }
            big--;
        }
        else if (carType == 2) {
            if (medium == 0) {
                return false;
            }
            medium--;
        }
        else {
            if (small == 0) {
                return false;
            }
            small--;
        }

        // a free slot was found and used
        return true;
    }
}

/**
 * Your ParkingSystem object will be instantiated and called as such:
 * ParkingSystem obj = new ParkingSystem(big, medium, small);
 * boolean param_1 = obj.addCar(carType);
 */
```

---

## Complexity Analysis

| Metric | Complexity | Reason |
|--------|------------|--------|
| Time | **O(1)** per `addCar` call | Only a few comparisons and one decrement |
| Space | **O(1)** | Only three integer counters are stored |

---

## Key Takeaways

- A car can only use its own type of slot, so three independent counters are enough.
- No array or list of cars is needed, because only the **number of free slots** matters.
- Decreasing the counter when a car parks keeps the state correct for the next call.
- Returning `false` early (before decreasing) makes sure the counter never goes below `0`.
