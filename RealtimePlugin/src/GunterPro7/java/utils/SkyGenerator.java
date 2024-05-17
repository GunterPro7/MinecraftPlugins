package utils;

import main.Main;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.generator.ChunkGenerator;


import java.util.Random;

public class SkyGenerator extends ChunkGenerator {
    /*@Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        // Create a chunk data object
        ChunkData chunkData = createChunkData(world);

        // Set the biome for the whole chunk to the void
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.THE_VOID);
            }
        }

        // Generate some flying islands with some probability
        if (random.nextDouble() < 0.1) {
            // Create a flying island
            int islandHeight = random.nextInt(100) + 50;
            int islandRadius = random.nextInt(10) + 5;

            // Generate the terrain for the flying island
            // This is just a simple example, you can use more complex algorithms and noise functions to create more realistic terrain
            for (int i = -islandRadius; i <= islandRadius; i++) {
                for (int j = -islandRadius; j <= islandRadius; j++) {
                    if (i * i + j * j <= islandRadius * islandRadius) {
                        // Set the bottom layer to stone
                        chunkData.setBlock(8 + i, islandHeight - 1, 8 + j, Material.STONE);

                        // Set the next layers to dirt and grass with some variation
                        int height = random.nextInt(3) + 2;
                        for (int k = 0; k < height; k++) {
                            Material material = k == height - 1 ? Material.GRASS_BLOCK : Material.DIRT;
                            chunkData.setBlock(8 + i, islandHeight + k, 8 + j, material);
                        }

                        // Add some trees and flowers with some probability
                        if (random.nextDouble() < 0.05) {
                            // Create a tree
                            int treeHeight = random.nextInt(5) + 4;
                            for (int k = 0; k < treeHeight; k++) {
                                chunkData.setBlock(8 + i, islandHeight + height + k, 8 + j, Material.OAK_LOG);
                            }
                            for (int l = -2; l <= 2; l++) {
                                for (int m = -2; m <= 2; m++) {
                                    for (int n = -2; n <= 2; n++) {
                                        if (Math.abs(l) + Math.abs(m) + Math.abs(n) <= 3) {
                                            chunkData.setBlock(8 + i + l, islandHeight + height + treeHeight - 1 + n, 8 + j + m, Material.OAK_LEAVES);
                                        }
                                    }
                                }
                            }
                        } else if (random.nextDouble() < 0.05) {
                            // Create a flower
                            Material flowerMaterial = random.nextBoolean() ? Material.POPPY : Material.DANDELION;
                            chunkData.setBlock(8 + i, islandHeight + height, 8 + j, flowerMaterial);
                        }
                    }
                }
            }

            // Add some villages with some probability
            if (random.nextDouble() < 0.01) {
                // Create a village
                int villageSize = random.nextInt(3) + 3;

                // Generate the houses for the village
                for (int h = 0; h < villageSize; h++) {
                    // Choose a random location for the house
                    int houseX = random.nextInt(16);
                    int houseZ = random.nextInt(16);
                    int houseY = world.getHighestBlockYAt(x * 16 + houseX, z * 16 + houseZ);

                    // Check if the location is valid for a house
                    if (houseY > islandHeight && world.getBlockAt(x * 16 + houseX, houseY - 1, z * 16 + houseZ).getType() == Material.GRASS_BLOCK) {
                        // Create a house
                        int houseWidth = random.nextInt(3) + 3;
                        int houseLength = random.nextInt(3) + 3;
                        int houseHeight = random.nextInt(3) + 3;

                        // Set the blocks for the house walls and floor
                        chunkData.setRegion(houseX - houseWidth / 2, houseY - 1, houseZ - houseLength / 2,
                                houseX + houseWidth / 2 + 1, houseY + houseHeight - 1, houseZ + houseLength / 2 + 1, Material.OAK_PLANKS);

                        // Set the blocks for the house roof
                        chunkData.setRegion(houseX - houseWidth / 2 - 1, houseY + houseHeight - 1, houseZ - houseLength / 2 - 1,
                                houseX + houseWidth / 2 + 2, houseY + houseHeight, houseZ + houseLength / 2 + 2, Material.OAK_STAIRS);

                        // Set the blocks for the house door
                        chunkData.setBlock(houseX, houseY, houseZ - houseLength / 2, Material.OAK_DOOR);

                        // Set the block data for the door
                        Door door = (Door) Material.OAK_DOOR.createBlockData();
                        door.setFacing(BlockFace.NORTH);
                        door.setHalf(Bisected.Half.BOTTOM);
                        chunkData.setBlock(houseX, houseY, houseZ - houseLength / 2, door);

                        // Set the block data for the upper part of the door
                        door.setHalf(Bisected.Half.TOP);
                        chunkData.setBlock(houseX, houseY + 1, houseZ - houseLength / 2, door);

                        // Set the blocks for the house windows
                        chunkData.setBlock(houseX - houseWidth / 2, houseY + 1, houseZ, Material.GLASS_PANE);
                        chunkData.setBlock(houseX + houseWidth / 2, houseY + 1, houseZ, Material.GLASS_PANE);
                    }
                }
            }
        }

        // Return the chunk data
        return chunkData;
    }*/ // This is the old good code - that is working for only plain islands

    public static final Biome[] biomeList = new Biome[]{Biome.PLAINS, Biome.DESERT, Biome.RIVER, Biome.BIRCH_FOREST, Biome.TAIGA, Biome.SNOWY_TAIGA};


    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        // Create a chunk data object
        ChunkData chunkData = createChunkData(world);

        // Set the biome for the whole chunk to the void
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.THE_VOID);
            }
        }

        if (x == 0 && z == 0) {
            chunkData.setBlock(0, 64, 0, Material.BEDROCK);
            return chunkData;
        }

        // Generate some flying islands with some probability
        if (random.nextDouble() < 0.01) {
            // Create a flying island
            int islandHeight = random.nextInt(100) + 50;
            int islandRadius = random.nextInt(10) + 5;

            // Choose a random biome for the island
            Biome islandBiome = biomeList[random.nextInt(biomeList.length)];

            // Generate the terrain for the flying island
            // This is just a simple example, you can use more complex algorithms and noise functions to create more realistic terrain
            for (int i = -islandRadius; i <= islandRadius; i++) {
                for (int j = -islandRadius; j <= islandRadius; j++) {
                    if (i * i + j * j <= islandRadius * islandRadius) {
                        // Set the biome for the island
                        biome.setBiome(8 + i, 8 + j, islandBiome);

                        // Set the bottom layer to stone
                        chunkData.setBlock(8 + i, islandHeight - 1, 8 + j, Material.STONE);

                        // Set the next layers to dirt and grass with some variation
                        int height = random.nextInt(3) + 2;
                        for (int k = 0; k < height; k++) {
                            Material material;
                            switch (islandBiome) {
                                case DESERT:
                                    material = k == height - 1 ? Material.SAND : Material.SANDSTONE;
                                    break;
                                case RIVER:
                                    material = k == height - 1 ? random.nextInt(4) == 0 ? Material.WATER : Material.GRASS_BLOCK : Material.DIRT;
                                    break;
                                default:
                                    material = k == height - 1 ? Material.GRASS_BLOCK : Material.DIRT;
                            }
                            //Material material = islandBiome != Biome.DESERT ? k == height - 1 ? Material.GRASS_BLOCK : Material.DIRT : k == height - 2 ? Material.SAND : Material.SANDSTONE;
                            chunkData.setBlock(8 + i, islandHeight + k, 8 + j, material);
                        }

                        // Add some trees and flowers with some probability depending on the biome
                        if (islandBiome == Biome.PLAINS) {
                            if (random.nextDouble() < 0.05) {
                                // Create a flower
                                Material flowerMaterial = random.nextBoolean() ? Material.POPPY : Material.DANDELION;
                                chunkData.setBlock(8 + i, islandHeight + height, 8 + j, flowerMaterial);
                            }
                        } else if (islandBiome == Biome.DESERT) {
                            if (random.nextDouble() < 0.05) {
                                // Create a cactus
                                int cactusHeight = random.nextInt(3) + 1;
                                for (int k = 0; k < cactusHeight; k++) {
                                    chunkData.setBlock(8 + i, islandHeight + height + k, 8 + j, Material.CACTUS);
                                }
                            }
                        } else if (islandBiome == Biome.RIVER) {
                            if (random.nextDouble() < 0.25 && isNextToWater(chunkData, i, islandHeight + height, j)) {
                                // Create a sugar cane
                                int sugarCaneHeight = random.nextInt(3) + 1;
                                for (int k = 0; k < sugarCaneHeight; k++) {
                                    chunkData.setBlock(8 + i, islandHeight + height + k, 8 + j, Material.SUGAR_CANE);
                                }
                            }
                        } else if (islandBiome == Biome.BIRCH_FOREST) {
                            if (random.nextDouble() < 0.05) {
                                // Create a birch tree
                                int treeHeight = random.nextInt(5) + 4;
                                for (int k = 0; k < treeHeight; k++) {
                                    chunkData.setBlock(8 + i, islandHeight + height + k, 8 + j, Material.BIRCH_LOG);
                                }
                                for (int l = -2; l <= 2; l++) {
                                    for (int m = -2; m <= 2; m++) {
                                        for (int n = -2; n <= 2; n++) {
                                            if (Math.abs(l) + Math.abs(m) + Math.abs(n) <= 3) {
                                                chunkData.setBlock(8 + i + l, islandHeight + height + treeHeight - 1 + n, 8 + j + m, Material.BIRCH_LEAVES);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (islandBiome == Biome.TAIGA) {
                            if (random.nextDouble() < 0.05) {
                                // Create a spruce tree
                                int treeHeight = random.nextInt(5) + 4;
                                for (int k = 0; k < treeHeight; k++) {
                                    chunkData.setBlock(8 + i, islandHeight + height + k, 8 + j, Material.SPRUCE_LOG);
                                }
                                for (int l = -2; l <= 2; l++) {
                                    for (int m = -2; m <= 2; m++) {
                                        for (int n = -2; n <= 2; n++) {
                                            if (Math.abs(l) + Math.abs(m) + Math.abs(n) <= 3) {
                                                chunkData.setBlock(8 + i + l, islandHeight + height + treeHeight - 1 + n, 8 + j + m, Material.SPRUCE_LEAVES);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (islandBiome == Biome.SNOWY_PLAINS) {
                            if (random.nextDouble() < 0.05) {
                                // Create a snow layer
                                chunkData.setBlock(8 + i, islandHeight + height, 8 + j, Material.SNOW);
                            }
                        }

                        if (random.nextDouble() < 0.0025) {
                            chunkData.setBlock(8 + i, islandHeight + height, 8 + j, Material.CHEST);
                        }
                    }
                }
            }

            // Add some villages with some probability
            /*if (random.nextDouble() < 0.01) {
                // Create a village
                int villageSize = random.nextInt(3) + 3;

                // Generate the houses for the village
                for (int h = 0; h < villageSize; h++) {
                    // Choose a random location for the house
                    int houseX = random.nextInt(16);
                    int houseZ = random.nextInt(16);
                    int houseY = world.getHighestBlockYAt(x * 16 + houseX, z * 16 + houseZ);

                    // Check if the location is valid for a house
                    if (houseY > islandHeight && world.getBlockAt(x * 16 + houseX, houseY - 1, z * 16 + houseZ).getType() == Material.GRASS_BLOCK) {
                        // Create a house
                        int houseWidth = random.nextInt(3) + 3;
                        int houseLength = random.nextInt(3) + 3;
                        int houseHeight = random.nextInt(3) + 3;

                        // Set the blocks for the house walls and floor
                        chunkData.setRegion(houseX - houseWidth / 2, houseY - 1, houseZ - houseLength / 2,
                                houseX + houseWidth / 2 + 1, houseY + houseHeight - 1, houseZ + houseLength / 2 + 1, Material.OAK_PLANKS);

                        // Set the blocks for the house roof
                        chunkData.setRegion(houseX - houseWidth / 2 - 1, houseY + houseHeight - 1, houseZ - houseLength / 2 - 1,
                                houseX + houseWidth / 2 + 2, houseY + houseHeight, houseZ + houseLength / 2 + 2, Material.OAK_STAIRS);

                        // Set the blocks for the house door
                        chunkData.setBlock(houseX, houseY, houseZ - houseLength / 2, Material.OAK_DOOR);

                        // Set the block data for the door
                        Door door = (Door) Material.OAK_DOOR.createBlockData();
                        door.setFacing(BlockFace.NORTH);
                        door.setHalf(Bisected.Half.BOTTOM);
                        chunkData.setBlock(houseX, houseY, houseZ - houseLength / 2, door);

                        // Set the block data for the upper part of the door
                        door.setHalf(Bisected.Half.TOP);
                        chunkData.setBlock(houseX, houseY + 1, houseZ - houseLength / 2, door);

                        // Set the blocks for the house windows
                        chunkData.setBlock(houseX - houseWidth / 2, houseY + 1, houseZ, Material.GLASS_PANE);
                        chunkData.setBlock(houseX + houseWidth / 2, houseY + 1, houseZ, Material.GLASS_PANE);
                    }
                }
            }*/
        }

        // Return the chunk data
        return chunkData;
    }

    private boolean isNextToWater(ChunkData chunkData, int x, int y, int z) {
        if (chunkData.getType(8 + x, y, 8 + z) != Material.WATER) {
            for (int i = 7; i < 10; i += 2) {
                for (int j = 7; j < 10; j += 2) {
                    if (chunkData.getType(i, y, j) == Material.WATER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // A method to generate a small house
    public void generateHouse(ChunkData chunkData, int x, int y, int z, Biome biome) {
        // Choose a random size for the house
        int houseWidth = Main.RANDOM.nextInt(5) + 3;
        int houseLength = Main.RANDOM.nextInt(5) + 3;
        int houseHeight = Main.RANDOM.nextInt(3) + 3;

        // Choose a random material for the house walls and floor depending on the biome
        Material wallMaterial;
        Material floorMaterial;
        if (biome == Biome.PLAINS) {
            wallMaterial = Material.OAK_PLANKS;
            floorMaterial = Material.OAK_PLANKS;
        } else if (biome == Biome.DESERT) {
            wallMaterial = Material.SANDSTONE;
            floorMaterial = Material.SANDSTONE;
        } else if (biome == Biome.RIVER) {
            wallMaterial = Material.COBBLESTONE;
            floorMaterial = Material.COBBLESTONE;
        } else if (biome == Biome.BIRCH_FOREST) {
            wallMaterial = Material.BIRCH_PLANKS;
            floorMaterial = Material.BIRCH_PLANKS;
        } else if (biome == Biome.TAIGA) {
            wallMaterial = Material.SPRUCE_PLANKS;
            floorMaterial = Material.SPRUCE_PLANKS;
        } else if (biome == Biome.SNOWY_TAIGA) {
            wallMaterial = Material.SNOW_BLOCK;
            floorMaterial = Material.SNOW_BLOCK;
        } else {
            // Default to oak planks
            wallMaterial = Material.OAK_PLANKS;
            floorMaterial = Material.OAK_PLANKS;
        }

        // Set the blocks for the house walls and floor
        chunkData.setRegion(x - houseWidth / 2, y - 1, z - houseLength / 2,
                x + houseWidth / 2 + 1, y + houseHeight - 1, z + houseLength / 2 + 1, wallMaterial);

        chunkData.setRegion(x - houseWidth / 2 + 1, y - 1, z - houseLength / 2 + 1,
                x + houseWidth / 2, y - 1, z + houseLength / 2, floorMaterial);

        // Set the blocks for the house roof
        chunkData.setRegion(x - houseWidth / 2 - 1, y + houseHeight - 1, z - houseLength / 2 - 1,
                x + houseWidth / 2 + 2, y + houseHeight, z + houseLength / 2 + 2, Material.OAK_STAIRS);

        // Set the blocks for the house door
        chunkData.setBlock(x, y, z - houseLength / 2, Material.OAK_DOOR);

        // Set the block data for the door
        Door door = (Door) Material.OAK_DOOR.createBlockData();
        door.setFacing(BlockFace.NORTH);
        door.setHalf(Bisected.Half.TOP);
        chunkData.setBlock(x, y, z - houseLength / 2, door);

        // Set the block data for the upper part of the door
        door.setHalf(Bisected.Half.BOTTOM);
        chunkData.setBlock(x, y + 1, z - houseLength / 2, door);

        // Set the blocks for the house windows
        chunkData.setBlock(x - houseWidth / 2, y + 1, z, Material.GLASS_PANE);
        chunkData.setBlock(x + houseWidth / 2, y + 1, z, Material.GLASS_PANE);
    } // TODO method from bing chat thingy

}