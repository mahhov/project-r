//package world;
//
//import geometry.CoordinateI3;
//import util.MathNumbers;
//
//class WorldChunkFiller implements Runnable {
//    private final int[][] heightMap;
//    private final WorldChunk worldChunk;
//    private final int width, length;
//    private final int offsetX, offsetY, offsetZ;
//
//    WorldChunkFiller(int[][] heightMap, int maxWidth, int maxLength, WorldChunk worldChunk) {
//        this.heightMap = heightMap;
//        this.worldChunk = worldChunk;
//
//        offsetX = worldChunk.getOffsetX();
//        offsetY = worldChunk.getOffsetY();
//        offsetZ = worldChunk.getOffsetZ();
//
//        width = MathNumbers.min(World.CHUNK_SIZE, maxWidth - offsetX);
//        length = MathNumbers.min(World.CHUNK_SIZE, maxLength - offsetX);
//    }
//
//    public void run() {
//        for (int x = 0; x < width; x++)
//            for (int y = 0; y < length; y++) {
//                int height = MathNumbers.min(heightMap[x + offsetX][y + offsetY] - offsetZ, World.CHUNK_SIZE);
//                for (int z = 0; z < height; z++)
//                    worldChunk.addCube(new CoordinateI3(x, y, z));
//            }
//    }
//}