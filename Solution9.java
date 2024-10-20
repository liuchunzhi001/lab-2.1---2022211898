import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 *
 * 给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
 *
 * 给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和  bi的人归入同一组。当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 4, dislikes = [[1,2],[1,3],[2,4]]
 * 输出：true
 * 解释：group1 [1,4], group2 [2,3]
 * 示例 2：
 *
 * 输入：n = 3, dislikes = [[1,2],[1,3],[2,3]]
 * 输出：false
 * 示例 3：
 *
 * 输入：n = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
 * 输出：false
 *
 *
 * 提示：
 *
 * 1 <= n <= 2000
 * 0 <= dislikes.length <= 104
 * dislikes[i].length == 2
 * 1 <= dislikes[i][j] <= n
 * ai < bi
 * dislikes 中每一组都 不同
 *
 */
class Solution9 {
    public boolean possibleBipartition(int n, int[][] dislikes) {
        int[] fa = new int[n + 1]; // 并查集，初始化为节点自身
        for (int i = 1; i <= n; i++) {
            fa[i] = i; // 初始化为自身
        }
        List<Integer>[] g = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] p : dislikes) {
            int a = p[0], b = p[1];
            g[a].add(b);
            g[b].add(a);
        }
        for (int i = 1; i <= n; i++) {
            for (int neighbor : g[i]) {
                if (findFa(i, fa) == findFa(neighbor, fa)) {
                    // 如果已经在同一个集合中，则无法分组
                    return false;
                }
                union(i, neighbor, fa);
            }
        }
        return true;
    }

    private void union(int x, int y, int[] fa) {
        int rootX = findFa(x, fa);
        int rootY = findFa(y, fa);
        if (rootX != rootY) {
            // 合并两个集合，这里简单地选择一个集合的代表作为新集合的代表
            fa[rootX] = -rootY; // 或者 fa[rootY] = -rootX，只要保证它们不同集合并且可以识别即可
            // 注意：这里使用负数是为了在后续可以通过 fa[i] < 0 来判断 i 是某个集合的代表
        }
    }

    private int findFa(int x, int[] fa) {
        if (fa[x] < 0) {
            // 如果是负数，则已经是代表
            return x;
        }
        // 路径压缩
        fa[x] = findFa(fa[x], fa);
        return fa[x];
    }
    // 测试类
    public static void main(String[] args) {
        Solution9 L2022211898_Solution9_Test.java = new Solution9();
        // 示例 1
        int[][] dislikes1 = {{1, 2}, {1, 3}, {2, 4}};
        System.out.println(L2022211898_Solution9_Test.java.possibleBipartition(4, dislikes1)); // 输出 true
        // 示例 2
        int[][] dislikes2 = {{1, 2}, {1, 3}, {2, 3}};
        System.out.println(L2022211898_Solution9_Test.java.possibleBipartition(3, dislikes2)); // 输出 false
        // 示例 3
        int[][] dislikes3 = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}};
        System.out.println(L2022211898_Solution9_Test.java.possibleBipartition(5, dislikes3)); // 输出 false
    }
}
