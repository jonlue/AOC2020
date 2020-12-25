package main.days;

import main.AOCRiddle;

import java.util.*;

public class Day20 extends AOCRiddle {
    public Day20(String in, int part) {
        super(in, part);
        parse();
        image_size = images.size();
        image_length = (int) Math.sqrt(image_size);
        corners = new int[]{0, image_length -1, image_size-image_length, image_size-1};
    }

    private final int[] corners;
    private final int image_size;
    private final int image_length;

    private Map<Integer, List<String[]>> images;
    private final LinkedList<String[]> imageArray = new LinkedList<>();
    private final LinkedList<Integer> imageId = new LinkedList<>();
    private String[] finalImage;

    @Override
    protected String solve1() {
        findNextImage();
        return String.valueOf(getProductOfCorners());
    }

    @Override
    protected String solve2() {
        findNextImage();
        removeBorders();
        compileImage();

        int seaMonsters = reorientAndFindSeaMonsters();
        int countHashtag = countHashtag();
        return String.valueOf(countHashtag - (15 * seaMonsters));
    }

    private int countHashtag() {
        int sum = 0;
        for(String s : finalImage){
            sum += s.chars().filter(ch -> ch == '#').count();
        }
        return sum;
    }

    private int reorientAndFindSeaMonsters() {
        int seaMonsters = findSeaMonsters();
        if(seaMonsters > 0){
            return seaMonsters;
        }
        for(int i = 0; i < 3; ++i){
            finalImage = rotateImage(finalImage);
            seaMonsters = findSeaMonsters();
            if(seaMonsters > 0){
                return seaMonsters;
            }
        }
        finalImage = flipImage(finalImage);
        seaMonsters = findSeaMonsters();
        if(seaMonsters > 0){
            return seaMonsters;
        }
        for(int i = 0; i < 3; ++i){
            finalImage = rotateImage(finalImage);
            seaMonsters = findSeaMonsters();
            if(seaMonsters > 0){
                return seaMonsters;
            }
        }
        return -1;
    }

    private int findSeaMonsters() {
        int sum = 0;
        for(int i = 1; i < finalImage.length-1; ++i){
            for(int j = 0; j< finalImage[i].length()-20; ++j){
                if(finalImage[i].charAt(j) == '#'){
                    if(findSeaMonster(i,j)){
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    private boolean findSeaMonster(int i, int j) {
        return (finalImage[i].charAt(j + 5) == '#' && finalImage[i].charAt(j + 6) == '#' && finalImage[i].charAt(j + 11) == '#' && finalImage[i].charAt(j + 12) == '#' && finalImage[i].charAt(j + 17) == '#' && finalImage[i].charAt(j + 18) == '#' && finalImage[i].charAt(j + 19) == '#') &&
                (finalImage[i+1].charAt(j + 1) == '#' && finalImage[i+1].charAt(j + 4) == '#' && finalImage[i+1].charAt(j + 7) == '#' && finalImage[i+1].charAt(j + 10) == '#' && finalImage[i+1].charAt(j + 13) == '#' && finalImage[i+1].charAt(j + 16) == '#') &&
                finalImage[i-1].charAt(j + 18) == '#';
    }

    private void compileImage() {
        List<List<String[]>> pic = new ArrayList<>();
        for(int i = 0; i < imageArray.size(); i += image_length){
            List<String[]> temp = new ArrayList<>();
            for(int j = i; j < i+image_length; ++j){
                temp.add(imageArray.get(j));
            }
            pic.add(temp);
        }

        int j = 0;
        finalImage = new String[imageArray.getFirst()[0].length() * image_length];
        for(List<String[]> l : pic){
            for(int i = 0; i <l.get(0)[0].length(); ++i){
                StringBuilder sb = new StringBuilder();
                for(String[] s : l){
                    sb.append(s[i]);
                }
                finalImage[j] = sb.toString();
                j++;
            }
        }
    }

    private void removeBorders(){
        //imageArray.forEach(a -> a = removeBorders(a));
        for(int i = 0; i < imageArray.size(); ++i){
            String[] temp = imageArray.get(i);
            imageArray.remove(i);
            imageArray.add(i,removeBorders(temp));
        }
    }

    private String[] removeBorders(String[] image) {
        String[] withoutBorder = new String[image.length-2];
        int j = 0;
        for(int i = 0; i< image.length; ++i){
            if(i == 0 ||i == image.length-1){
                continue;
            }
            withoutBorder[j] = image[i].substring(1,image[i].length()-1);
            j++;
        }
        return withoutBorder;
    }

    private void findNextImage(){
        boolean found = false;
        for(int k : images.keySet()){
            imageId.add(k);
            for(String[] i : images.get(k)){
                imageArray.add(i);
                findNextImage(imageArray, imageId);
                if(imageArray.size() == image_size){
                    found = true;
                    break;
                }
                imageArray.remove(0);
            }
            if(found){
                break;
            }
            imageId.remove(0);
        }
    }

    private void findNextImage(LinkedList<String[]> image, LinkedList<Integer> imageIds) {
        if(image.size() == image_size) {
            return;
        }
        for (int k : images.keySet()) {
            if (imageAlreadyUsed(imageIds, k)) {
                continue;
            }
            for (String[] i : images.get(k)) {
                boolean top = true;
                boolean side = true;
                if(image.size()>=image_length){
                    top = compareBottom(i,image.get(image.size()-image_length));
                }
                if(image.size() % image_length != 0) {
                    side = compareSide(image.get(image.size()-1), i);
                }
                if(top && side){
                    image.add(i);
                    imageIds.add(k);
                    findNextImage(image,imageIds);
                    if(image.size() != image_size) {
                        image.removeLast();
                        imageIds.removeLast();
                    }else {
                        return;
                    }
                }
            }
        }
    }

    private boolean imageAlreadyUsed(List<Integer> ids, int id){
        return ids.contains(id);
    }

    private boolean compareSide(String[] left, String[] right){
        for(int i = 0; i < left.length; ++i){
            char end = left[i].charAt(left[i].length()-1);
            char start = right[i].charAt(0);
            if (end != start) {
                return false;
            }
        }
        return true;
    }

    private boolean compareBottom(String[] bottom, String[] top){
        return top[top.length-1].equals(bottom[0]);
    }

    private long getProductOfCorners() {
        long product = 1;
        for (int corner : corners) {
            product *= imageId.get(corner);
        }
        return product;
    }

    private void parse(){
        images = new HashMap<>();
        for(String s : getInput().split("\n\n")){
            List<String[]> imagePossibilities = new ArrayList<>();
            String[] lines = s.split("\n");
            int id = Integer.parseInt(lines[0].replaceAll("Tile ","").replaceAll(":",""));
            // normal

            String[] image = getImage(lines);
            imagePossibilities.add(image);
            String[] rotated = new String[image.length];
            System.arraycopy(image,0,rotated,0,image.length);
            for(int i = 0; i <3; ++i){
                rotated = rotateImage(rotated);
                imagePossibilities.add(rotated);
            }
            String[] flipped = flipImage(image);
            imagePossibilities.add(flipped);
            rotated = flipped;
            for(int i = 0; i < 3; ++i){
                rotated = rotateImage(rotated);
                imagePossibilities.add(rotated);
            }
            images.put(id,imagePossibilities);
        }

    }

    private String[] getImage(String[] lines){
        int count = 0;
        String[] image = new String[lines.length-1];
        for(int i = 1; i < lines.length; ++i){
            image[count] = lines[i];
            count++;
        }
        return image;
    }

    private String[] flipImage(String[] lines){
        String[] image = new String[lines.length];
        for(int i = 0; i < lines.length; ++i){
            StringBuilder sb = new StringBuilder(lines[i]);
            image[i] = sb.reverse().toString();
        }
        return image;
    }

    private String[] rotateImage(String[] rotated){
        String[] image = new String[rotated.length];
        for(int i = 0; i < rotated.length; ++i){
            StringBuilder sb = new StringBuilder();
            for(int j = image.length-1; j >= 0; --j){
                sb.append(rotated[j].charAt(i));
            }
            image[i] = sb.toString();
        }
        return image;
    }
}
