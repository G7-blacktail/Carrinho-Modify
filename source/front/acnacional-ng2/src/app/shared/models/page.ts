export class Page<T> {
  content?: Array<T>;
  size?: number;
  number?: number;
  numberOfElements?: number;
  totalPages?: number;
  totalElements?: number;
  sort?: string;
  last?: boolean;
  first?: boolean;
}
